package com.thanhtam.linhsondich.fragment

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.thanhtam.linhsondich.R
import com.thanhtam.linhsondich.core.HuyenKhongLogic
import com.thanhtam.linhsondich.core.MountainInfo
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

// Cấu trúc dữ liệu chi tiết cho từng khoảng độ nhỏ trong một Sơn
data class SonViChiTiet(
    val tenSon: String,      // Tên Sơn vị (ví dụ: Sửu)
    val startDeg: Float,     // Độ bắt đầu
    val endDeg: Float,       // Độ kết thúc
    val phiTinh: Int,        // Số phi tinh của cung Bát quái
    val isChinhHuong: Boolean, // Là chính hướng hay kiêm hướng?
    val theQuai: Int?,       // Sao thay thế (nếu có, null nếu là chính hướng)
    val amDuong: String,     // "Dương" hoặc "Âm"
    val nguyenlong: String   // "Thiên" hoặc "Địa" hoặc "Nhân"
)

class LaBanProView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 1. ĐỊNH NGHĨA CẤU TRÚC DỮ LIỆU
    private data class SonVi(val name: String, val startDeg: Float, val endDeg: Float)
    private data class MagicSquareCell(
        val vanTinh: String,  // Số ở giữa
        val sonTinh: String, // Số bên trái (Tọa)
        val huongTinh: String // Số bên phải (Hướng)
    )

    data class HkExtraInfo(
        val khongVi: String,
        val thanhMonChinh: String,
        val thanhMonPhu: String
    )

    // 2. KHAI BÁO CÁC BIẾN DỮ LIỆU VÀ TRẠNG THÁI
    private val hkLogic = HuyenKhongLogic()

    // Danh sách 24 sơn vị đơn giản chỉ để VẼ VÒNG NGOÀI
    private val sonViList = listOf(
        SonVi("Nhâm", 337.5f, 352.5f), SonVi("Tý", 352.5f, 7.5f), SonVi("Quý", 7.5f, 22.5f),
        SonVi("Sửu", 22.5f, 37.5f), SonVi("Cấn", 37.5f, 52.5f), SonVi("Dần", 52.5f, 67.5f),
        SonVi("Giáp", 67.5f, 82.5f), SonVi("Mão", 82.5f, 97.5f), SonVi("Ất", 97.5f, 112.5f),
        SonVi("Thìn", 112.5f, 127.5f), SonVi("Tốn", 127.5f, 142.5f), SonVi("Tỵ", 142.5f, 157.5f),
        SonVi("Bính", 157.5f, 172.5f), SonVi("Ngọ", 172.5f, 187.5f), SonVi("Đinh", 187.5f, 202.5f),
        SonVi("Mùi", 202.5f, 217.5f), SonVi("Khôn", 217.5f, 232.5f), SonVi("Thân", 232.5f, 247.5f),
        SonVi("Canh", 247.5f, 262.5f), SonVi("Dậu", 262.5f, 277.5f), SonVi("Tân", 277.5f, 292.5f),
        SonVi("Tuất", 292.5f, 307.5f), SonVi("Càn", 307.5f, 322.5f), SonVi("Hợi", 322.5f, 337.5f)
    )

    // BẢNG DỮ LIỆU CHI TIẾT ĐỂ TÍNH TOÁN KIÊM HƯỚNG
    private val sonViChiTietList = listOf(
        // CUNG KHẢM (1)
        SonViChiTiet("Nhâm", 337.6f, 341.9f, 1, false, 2, "Dương", "Địa"),
        SonViChiTiet("Nhâm", 342.0f, 348.9f, 1, true, null, "Dương", "Địa"),
        SonViChiTiet("Nhâm", 349.0f, 352.5f, 1, false, 2, "Dương", "Địa"),

        SonViChiTiet("Tý", 352.6f, 356.9f, 1, false, 1, "Âm", "Thiên"),
        SonViChiTiet("Tý", 357.0f, 3.9f, 1, true, null, "Âm", "Thiên"),
        SonViChiTiet("Tý", 4.0f, 7.5f, 1, false, 1, "Âm", "Thiên"),

        SonViChiTiet("Quý", 7.6f, 11.9f, 1, false, 1, "Âm", "Nhân"),
        SonViChiTiet("Quý", 12.0f, 18.9f, 1, true, null, "Âm", "Nhân"),
        SonViChiTiet("Quý", 19.0f, 22.5f, 1, false, 1, "Âm", "Nhân"),

        // CUNG CẤN (8)
        SonViChiTiet("Sửu", 22.6f, 26.9f, 8, false, 7, "Âm", "Địa"),
        SonViChiTiet("Sửu", 27.0f, 33.9f, 8, true, null, "Âm", "Địa"),
        SonViChiTiet("Sửu", 34.0f, 37.5f, 8, false, 7, "Âm", "Địa"),

        SonViChiTiet("Cấn", 37.6f, 41.9f, 8, false, 7, "Dương", "Thiên"),
        SonViChiTiet("Cấn", 42.0f, 48.9f, 8, true, null, "Dương", "Thiên"),
        SonViChiTiet("Cấn", 49.0f, 52.5f, 8, false, 7, "Dương", "Thiên"),

        SonViChiTiet("Dần", 52.6f, 56.9f, 8, false, 9, "Dương", "Nhân"),
        SonViChiTiet("Dần", 57.0f, 63.9f, 8, true, null, "Dương", "Nhân"),
        SonViChiTiet("Dần", 64.0f, 67.5f, 8, false, 9, "Dương", "Nhân"),

        // CUNG CHẤN (3)
        SonViChiTiet("Giáp", 67.6f, 71.9f, 3, false, 1, "Dương", "Địa"),
        SonViChiTiet("Giáp", 72.0f, 78.9f, 3, true, null, "Dương", "Địa"),
        SonViChiTiet("Giáp", 79.0f, 82.5f, 3, false, 1, "Dương", "Địa"),

        SonViChiTiet("Mão", 82.6f, 86.9f, 3, false, 2, "Âm", "Thiên"),
        SonViChiTiet("Mão", 87.0f, 93.9f, 3, true, null, "Âm", "Thiên"),
        SonViChiTiet("Mão", 94.0f, 97.5f, 3, false, 2, "Âm", "Thiên"),

        SonViChiTiet("Ất", 97.6f, 101.9f, 3, false, 2, "Âm", "Nhân"),
        SonViChiTiet("Ất", 102.0f, 108.9f, 3, true, null, "Âm", "Nhân"),
        SonViChiTiet("Ất", 109.0f, 112.5f, 3, false, 2, "Âm", "Nhân"),

        // CUNG TỐN (4)
        SonViChiTiet("Thìn", 112.6f, 116.9f, 4, false, 6, "Âm", "Địa"),
        SonViChiTiet("Thìn", 117.0f, 123.9f, 4, true, null, "Âm", "Địa"),
        SonViChiTiet("Thìn", 124.0f, 127.5f, 4, false, 6, "Âm", "Địa"),

        SonViChiTiet("Tốn", 127.6f, 131.9f, 4, false, 6, "Dương", "Thiên"),
        SonViChiTiet("Tốn", 132.0f, 138.9f, 4, true, null, "Dương", "Thiên"),
        SonViChiTiet("Tốn", 139.0f, 142.5f, 4, false, 6, "Dương", "Thiên"),

        SonViChiTiet("Tỵ", 142.6f, 146.9f, 4, false, 6, "Dương", "Nhân"),
        SonViChiTiet("Tỵ", 147.0f, 153.9f, 4, true, null, "Dương", "Nhân"),
        SonViChiTiet("Tỵ", 154.0f, 157.5f, 4, false, 6, "Dương", "Nhân"),

        // CUNG LY (9)
        SonViChiTiet("Bính", 157.6f, 161.9f, 9, false, 7, "Dương", "Địa"),
        SonViChiTiet("Bính", 162.0f, 168.9f, 9, true, null, "Dương", "Địa"),
        SonViChiTiet("Bính", 169.0f, 172.5f, 9, false, 7, "Dương", "Địa"),

        SonViChiTiet("Ngọ", 172.6f, 176.9f, 9, false, 9, "Âm", "Thiên"),
        SonViChiTiet("Ngọ", 177.0f, 183.9f, 9, true, null, "Âm", "Thiên"),
        SonViChiTiet("Ngọ", 184.0f, 187.5f, 9, false, 9, "Âm", "Thiên"),

        SonViChiTiet("Đinh", 187.6f, 191.9f, 9, false, 9, "Âm", "Nhân"),
        SonViChiTiet("Đinh", 192.0f, 198.9f, 9, true, null, "Âm", "Nhân"),
        SonViChiTiet("Đinh", 199.0f, 202.5f, 9, false, 9, "Âm", "Nhân"),

        // CUNG KHÔN (2)
        SonViChiTiet("Mùi", 202.6f, 206.9f, 2, false, 2, "Âm", "Địa"),
        SonViChiTiet("Mùi", 207.0f, 213.9f, 2, true, null, "Âm", "Địa"),
        SonViChiTiet("Mùi", 214.0f, 217.5f, 2, false, 2, "Âm", "Địa"),

        SonViChiTiet("Khôn", 217.6f, 221.9f, 2, false, 2, "Dương", "Thiên"),
        SonViChiTiet("Khôn", 222.0f, 228.9f, 2, true, null, "Dương", "Thiên"),
        SonViChiTiet("Khôn", 229.0f, 232.5f, 2, false, 2, "Dương", "Thiên"),

        SonViChiTiet("Thân", 232.6f, 236.9f, 2, false, 1, "Dương", "Nhân"),
        SonViChiTiet("Thân", 237.0f, 243.9f, 2, true, null, "Dương", "Nhân"),
        SonViChiTiet("Thân", 244.0f, 247.5f, 2, false, 1, "Dương", "Nhân"),

        // CUNG ĐOÀI (7)
        SonViChiTiet("Canh", 247.6f, 251.9f, 7, false, 9, "Dương", "Địa"),
        SonViChiTiet("Canh", 252.0f, 258.9f, 7, true, null, "Dương", "Địa"),
        SonViChiTiet("Canh", 259.0f, 262.5f, 7, false, 9, "Dương", "Địa"),

        SonViChiTiet("Dậu", 262.6f, 266.9f, 7, false, 7, "Âm", "Thiên"),
        SonViChiTiet("Dậu", 267.0f, 273.9f, 7, true, null, "Âm", "Thiên"),
        SonViChiTiet("Dậu", 274.0f, 277.5f, 7, false, 7, "Âm", "Thiên"),

        SonViChiTiet("Tân", 277.6f, 281.9f, 7, false, 7, "Âm", "Nhân"),
        SonViChiTiet("Tân", 282.0f, 288.9f, 7, true, null, "Âm", "Nhân"),
        SonViChiTiet("Tân", 289.0f, 292.5f, 7, false, 7, "Âm", "Nhân"),

        // CUNG CÀN (6)
        SonViChiTiet("Tuất", 292.6f, 296.9f, 6, false, 6, "Âm", "Địa"),
        SonViChiTiet("Tuất", 297.0f, 303.9f, 6, true, null, "Âm", "Địa"),
        SonViChiTiet("Tuất", 304.0f, 307.5f, 6, false, 6, "Âm", "Địa"),

        SonViChiTiet("Càn", 307.6f, 311.9f, 6, false, 6, "Dương", "Thiên"),
        SonViChiTiet("Càn", 312.0f, 318.9f, 6, true, null, "Dương", "Thiên"),
        SonViChiTiet("Càn", 319.0f, 322.5f, 6, false, 6, "Dương", "Thiên"),

        SonViChiTiet("Hợi", 322.6f, 326.9f, 6, false, 6, "Dương", "Nhân"),
        SonViChiTiet("Hợi", 327.0f, 333.9f, 6, true, null, "Dương", "Nhân"),
        SonViChiTiet("Hợi", 334.0f, 337.5f, 6, false, 6, "Dương", "Nhân")
    )


    // Dữ liệu ma phương, sẽ được tính toán lại
    private val magicSquareData = Array(3) { Array(3) { MagicSquareCell("", "", "") } }

    // Các biến trạng thái tính toán
    private var currentVan: Int = 9
    private var currentToa: Int = 5
    private var currentHuong: Int = 1
    private var toaIsThuan: Boolean = true
    private var huongIsThuan: Boolean = true

    // Các hằng số đường bay
    private val luongThienXichPath = listOf(5, 6, 7, 8, 9, 1, 2, 3, 4) // Thuận
    private val luongThienXichPathReverse = listOf(5, 4, 3, 2, 1, 9, 8, 7, 6) // Nghịch

    // Các biến thuộc tính của View
    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f
    private var arrowAngle: Float = 0f
    private var arrowExtension = 0f

    // Listener để trả về thông tin bổ sung
    private var extraInfoListener: ((HkExtraInfo) -> Unit)? = null

    fun setOnExtraInfoListener(listener: (HkExtraInfo) -> Unit) {
        this.extraInfoListener = listener
    }

    // 3. KHAI BÁO CÁC ĐỐI TƯỢNG PAINT
    private val paintLine =
        Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#f2eeed"); strokeWidth = 2f }
    private val paintTickLine =
        Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.GRAY; strokeWidth = 1.5f }
    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE; textAlign = Paint.Align.CENTER; textSize = 22f
    }
    private val paintDegreeText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK; textAlign = Paint.Align.CENTER; textSize = 22f
    }
    private val paintArrow = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.lsred); strokeWidth = 5f; strokeCap =
        Paint.Cap.ROUND
    }
    private val paintLsRedFill = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.lsred); style = Paint.Style.FILL
    }
    private val paintWhiteStroke = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE; style = Paint.Style.STROKE; strokeWidth = 2f
    }
    private val paintMagicSquare = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#7d7c7c"); style = Paint.Style.STROKE; strokeWidth = 2f
    }
    private val paintMagicSquareText =
        Paint(Paint.ANTI_ALIAS_FLAG).apply { textAlign = Paint.Align.CENTER }
    private val paintBitmap = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bgBitmap: Bitmap? = null

    // 4. KHỐI KHỞI TẠO (init)
    init {
        bgBitmap = BitmapFactory.decodeResource(resources, R.drawable.cuucung_background)
        arrowExtension = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            8f * 1.72f,
            resources.displayMetrics
        )

        // Tính toán đầy đủ ngay từ lần đầu tiên với hướng 0 độ
        tinhToaHuong(0f)
        tinhToanPhiTinh()
    }

    // 5. CÁC HÀM PUBLIC ĐỂ CẬP NHẬT TỪ BÊN NGOÀI
    fun updateDirection(degrees: Float) {
        arrowAngle = degrees
        // Khi độ thay đổi, tính lại toàn bộ chuỗi logic
        tinhToaHuong(degrees)
        tinhToanPhiTinh()
        invalidate() // Yêu cầu vẽ lại la bàn
    }

    fun updateVan(van: Int) {
        currentVan = van
        // Khi Vận thay đổi, CŨNG phải tính lại toàn bộ chuỗi logic với độ số hiện tại
        tinhToaHuong(arrowAngle)
        tinhToanPhiTinh()
        invalidate()
    }


    // 6. CÁC HÀM OVERRIDE CỦA VIEW
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val viewSize = min(w, h)
        centerX = w / 2f
        centerY = h / 2f
        radius = viewSize / 2f * 0.8f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawDegreeRing(canvas)
        drawSonViRing(canvas)
        drawCuuCungBackground(canvas)
        drawMagicSquare(canvas)
        drawArrow(canvas)
    }

    // 7. CÁC HÀM VẼ (draw...)
    private fun drawDegreeRing(canvas: Canvas) {
        val outerRadius = radius * 0.95f
        val innerRadius = radius * 0.85f
        val textPadding =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics)
        val textRadius = outerRadius + textPadding + paintDegreeText.textSize / 2
        val whiteFillPaint =
            Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL; color = Color.WHITE }
        val path = Path().apply {
            addCircle(centerX, centerY, outerRadius, Path.Direction.CW)
            addCircle(centerX, centerY, innerRadius, Path.Direction.CCW)
        }
        canvas.drawPath(path, whiteFillPaint)
        canvas.drawCircle(centerX, centerY, outerRadius, paintLine)
        canvas.drawCircle(centerX, centerY, innerRadius, paintLine)

        // [SỬA ĐỔI] Lặp qua tất cả 360 độ để tăng độ chi tiết
        for (i in 0 until 360) {
            val angleRad = Math.toRadians((i + 180).toDouble() - 90)
            val cosA = cos(angleRad).toFloat()
            val sinA = sin(angleRad).toFloat()

            // Xác định loại vạch và độ dài tương ứng
            val lineStart: Float
            val paintToUse: Paint

            when {
                // Vạch dài nhất, có số (0, 10, 20...)
                i % 10 == 0 -> {
                    lineStart = innerRadius * 1.02f
                    paintToUse = paintLine // Dùng paint chính
                }
                // Vạch trung bình (5, 15, 25...)
                i % 5 == 0 -> {
                    lineStart = innerRadius * 1.05f
                    paintToUse = paintTickLine // Dùng paint phụ
                }
                // Vạch ngắn nhất cho mỗi độ (1, 2, 3, 4, 6...)
                else -> {
                    lineStart = innerRadius * 1.08f
                    paintToUse = paintTickLine
                }
            }

            // Vẽ đường kẻ vạch
            canvas.drawLine(
                centerX + lineStart * cosA,
                centerY + lineStart * sinA,
                centerX + outerRadius * cosA,
                centerY + outerRadius * sinA,
                paintToUse
            )

            // Chỉ vẽ chữ số cho các vạch chia hết cho 10
            if (i % 10 == 0) {
                val text = i.toString()
                val x = centerX + textRadius * cosA
                val y = centerY + textRadius * sinA + paintDegreeText.textSize / 3
                canvas.save()
                canvas.rotate(i.toFloat() + 180, x, y)
                canvas.drawText(text, x, y, paintDegreeText)
                canvas.restore()
            }
        }
    }


    private fun drawSonViRing(canvas: Canvas) {
        val outerRadius = radius * 0.84f
        val innerRadius = radius * 0.7f
        val nameTextRadius = (outerRadius + innerRadius) / 2
        val path = Path().apply {
            addCircle(centerX, centerY, outerRadius, Path.Direction.CW)
            addCircle(centerX, centerY, innerRadius, Path.Direction.CCW)
        }
        canvas.drawPath(path, paintLsRedFill)
        canvas.drawCircle(centerX, centerY, outerRadius, paintWhiteStroke)
        canvas.drawCircle(centerX, centerY, innerRadius, paintWhiteStroke)

        sonViList.forEach { sonVi ->
            val canvasAngle = sonVi.startDeg + 180 - 90
            val angleRad = Math.toRadians(canvasAngle.toDouble()).toFloat()
            canvas.drawLine(
                centerX + innerRadius * cos(angleRad),
                centerY + innerRadius * sin(angleRad),
                centerX + outerRadius * cos(angleRad),
                centerY + outerRadius * sin(angleRad),
                paintWhiteStroke
            )
            val midAngleDeg = (sonVi.startDeg + 7.5f + 180) % 360
            canvas.save()
            canvas.rotate(midAngleDeg, centerX, centerY)
            val textBounds = Rect()
            paintText.getTextBounds(sonVi.name, 0, sonVi.name.length, textBounds)
            val textY = centerY - nameTextRadius - textBounds.exactCenterY()
            canvas.drawText(sonVi.name, centerX, textY, paintText)
            canvas.restore()

        }
    }

    private fun drawCuuCungBackground(canvas: Canvas) {
        bgBitmap?.let { bmp ->
            val imageRadius = radius * 0.7f
            val destRect = RectF(
                centerX - imageRadius,
                centerY - imageRadius,
                centerX + imageRadius,
                centerY + imageRadius
            )
            paintBitmap.alpha = 54
            canvas.drawBitmap(bmp, null, destRect, paintBitmap)
            paintBitmap.alpha = 255
        }
    }

    private fun drawMagicSquare(canvas: Canvas) {
        val innerRadius = radius * 0.7f
        val boxSize = innerRadius * 2 * 0.7f
        val startX = centerX - boxSize / 2
        val startY = centerY - boxSize / 2
        val cellSize = boxSize / 3
        val endX = centerX + boxSize / 2
        val endY = centerY + boxSize / 2

        // Vẽ lưới ma phương
        canvas.drawRect(startX, startY, endX, endY, paintMagicSquare)
        canvas.drawLine(startX + cellSize, startY, startX + cellSize, endY, paintMagicSquare)
        canvas.drawLine(
            startX + 2 * cellSize,
            startY,
            startX + 2 * cellSize,
            endY,
            paintMagicSquare
        )
        canvas.drawLine(startX, startY + cellSize, endX, startY + cellSize, paintMagicSquare)
        canvas.drawLine(
            startX,
            startY + 2 * cellSize,
            endX,
            startY + 2 * cellSize,
            paintMagicSquare
        )

        // Vẽ các số đã được tính toán
        val mainTextSize = 22f * 1.8f
        val sideTextSize = 18f * 1.8f
        val textBounds = Rect()
        paintMagicSquareText.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

        for (row in 0..2) {
            for (col in 0..2) {
                val cellData = magicSquareData[row][col]
                val cellCenterX = startX + (col * cellSize) + (cellSize / 2)
                val cellCenterY = startY + (row * cellSize) + (cellSize / 2)

                // 1. VẼ SỐ VẬN TINH (Ở GIỮA)
                val vanText = cellData.vanTinh
                if (vanText.isNotEmpty()) {
                    paintMagicSquareText.color = getColorForNumber(vanText)
                    paintMagicSquareText.textSize = mainTextSize
                    paintMagicSquareText.getTextBounds(vanText, 0, vanText.length, textBounds)
                    val vanTextY = cellCenterY - textBounds.exactCenterY()
                    canvas.drawText(vanText, cellCenterX, vanTextY, paintMagicSquareText)
                }

                // 2. VẼ SỐ TỌA TINH (TRÊN, BÊN TRÁI)
                val sonText = cellData.sonTinh
                if (sonText.isNotEmpty()) {
                    paintMagicSquareText.color = getColorForNumber(sonText)
                    paintMagicSquareText.textSize = sideTextSize
                    val sonTinhX = cellCenterX - (cellSize / 3.5f)
                    val sonTinhY = cellCenterY - (cellSize / 3.5f)
                    paintMagicSquareText.getTextBounds(sonText, 0, sonText.length, textBounds)
                    canvas.drawText(
                        sonText,
                        sonTinhX,
                        sonTinhY - textBounds.exactCenterY(),
                        paintMagicSquareText
                    )
                }

                // 3. VẼ SỐ HƯỚNG TINH (TRÊN, BÊN PHẢI)
                val huongText = cellData.huongTinh
                if (huongText.isNotEmpty()) {
                    paintMagicSquareText.color = getColorForNumber(huongText)
                    paintMagicSquareText.textSize = sideTextSize
                    val huongTinhX = cellCenterX + (cellSize / 3.5f)
                    val huongTinhY = cellCenterY - (cellSize / 3.5f)
                    paintMagicSquareText.getTextBounds(huongText, 0, huongText.length, textBounds)
                    canvas.drawText(
                        huongText,
                        huongTinhX,
                        huongTinhY - textBounds.exactCenterY(),
                        paintMagicSquareText
                    )
                }
            }
        }
        paintMagicSquareText.color = Color.parseColor("#7d7c7c")
    }

    private fun drawArrow(canvas: Canvas) {
        canvas.save()
        canvas.rotate(arrowAngle + 180, centerX, centerY)
        val arrowTip = centerY - (radius + arrowExtension)
        val tailBase = centerY + (radius + arrowExtension)
        val headLength = 13f
        val tailWidth = 27f
        canvas.drawLine(centerX, arrowTip, centerX, tailBase, paintArrow)
        canvas.drawLine(
            centerX,
            arrowTip,
            centerX - headLength / 2,
            arrowTip + headLength,
            paintArrow
        )
        canvas.drawLine(
            centerX,
            arrowTip,
            centerX + headLength / 2,
            arrowTip + headLength,
            paintArrow
        )
        canvas.drawLine(
            centerX - tailWidth / 2,
            tailBase,
            centerX + tailWidth / 2,
            tailBase,
            paintArrow
        )
        canvas.restore()
    }

    // 8. CÁC HÀM LOGIC TÍNH TOÁN

    /**
     * Hàm xác định Sao nhập trung cung và Chiều bay cho Tọa/Hướng tinh.
     * @param starFromVanBan Sao tìm được trên Vận bàn tại vị trí Tọa/Hướng.
     * @param houseLong Nguyên Long của nhà (Thiên/Địa/Nhân).
     * @param isChinhHuong Có phải là chính hướng hay không.
     */
    private fun getStarAndDirection(
        starFromVanBan: Int,
        houseLong: String,
        isChinhHuong: Boolean
    ): Pair<Int, Boolean> {
        // 1. Xác định cung gốc của sao đó trên địa bàn. Nếu sao là 5, dùng cung của Vận hiện tại.
        val originalPalace = if (starFromVanBan == 5) currentVan else starFromVanBan

        // 2. Trong cung gốc đó, tìm sơn vị có cùng Nguyên Long với nhà.
        val targetMountainInOriginalPalace =
            sonViChiTietList.find { it.phiTinh == originalPalace && it.nguyenlong == houseLong }
                ?: sonViChiTietList.first()

        // 3. Xác định sao nhập trung cung.
        val finalStar = if (isChinhHuong) {
            starFromVanBan
        } else {
            // Lấy sao Thế Quái tại sơn vị đó ở cung gốc. Nếu không có, dùng sao gốc.
            targetMountainInOriginalPalace.theQuai ?: starFromVanBan
        }

        // 4. Chiều bay (Thuận/Nghịch) dựa trên thuộc tính Âm Dương của sơn vị ĐÃ TÌM ĐƯỢC ở cung gốc.
        val isThuan = (targetMountainInOriginalPalace.amDuong == "Dương")

        return Pair(finalStar, isThuan)
    }

    private fun tinhToaHuong(facingDegrees: Float) {
        // Coi facingDegrees là hướng Hướng, tính hướng Tọa (đối diện)
        val toaDegrees = (facingDegrees + 180) % 360

        // Lấy thông tin nhà tại cung Hướng và cung Tọa
        val huongHouseInfo = sonViChiTietList.find {
            if (it.startDeg > it.endDeg) (facingDegrees >= it.startDeg || facingDegrees < it.endDeg)
            else (facingDegrees >= it.startDeg && facingDegrees < it.endDeg)
        } ?: sonViChiTietList.first()

        val toaHouseInfo = sonViChiTietList.find {
            if (it.startDeg > it.endDeg) (toaDegrees >= it.startDeg || toaDegrees < it.endDeg)
            else (toaDegrees >= it.startDeg && toaDegrees < it.endDeg)
        } ?: sonViChiTietList.first()

        // Bước 1: An Vận bàn để tìm Vận tinh tại cung Tọa và cung Hướng của nhà
        val vanTinhMap = anVanBanTamThoi()

        // Bước 2: Xác định sao Tọa nhập trung cung và chiều bay
        val starAtToaPalace = vanTinhMap[toaHouseInfo.phiTinh] ?: 5
        val (finalToa, toaThuan) = getStarAndDirection(
            starAtToaPalace,
            toaHouseInfo.nguyenlong,
            toaHouseInfo.isChinhHuong
        )
        currentToa = finalToa
        toaIsThuan = toaThuan

        // Bước 3: Xác định sao Hướng nhập trung cung và chiều bay
        val starAtHuongPalace = vanTinhMap[huongHouseInfo.phiTinh] ?: 5
        val (finalHuong, huongThuan) = getStarAndDirection(
            starAtHuongPalace,
            huongHouseInfo.nguyenlong,
            huongHouseInfo.isChinhHuong
        )
        currentHuong = finalHuong
        huongIsThuan = huongThuan

        // TÍNH TOÁN THÔNG TIN BỔ SUNG (GỌI LOGIC ĐA NỀN TẢNG)
        tinhThongTinBoSung(facingDegrees)
    }

    private fun tinhThongTinBoSung(facingDegrees: Float) {
        val extraInfo = hkLogic.calculateExtraInfo(facingDegrees, currentVan)
        extraInfoListener?.invoke(HkExtraInfo(
            khongVi = extraInfo.khongVi,
            thanhMonChinh = extraInfo.thanhMonChinh,
            thanhMonPhu = extraInfo.thanhMonPhu
        ))
    }

    private fun anVanBanTamThoi(): Map<Int, Int> {
        val vanTinhMap = mutableMapOf<Int, Int>()
        var vanHienTai = currentVan
        vanTinhMap[5] = vanHienTai

        val vanPath = listOf(6, 7, 8, 9, 1, 2, 3, 4)
        for (palaceNumber in vanPath) {
            vanHienTai = if (vanHienTai == 9) 1 else vanHienTai + 1
            vanTinhMap[palaceNumber] = vanHienTai
        }
        return vanTinhMap
    }

    private fun tinhToanPhiTinh() {
        val tinhBanData = mapOf(
            5 to calculateTrungCung(),
            6 to calculateCungCan(),
            7 to calculateCungDoai(),
            8 to calculateCungCanCung(),
            9 to calculateCungLy(),
            1 to calculateCungKham(),
            2 to calculateCungKhon(),
            3 to calculateCungChan(),
            4 to calculateCungTon()
        )

        for ((palace, data) in tinhBanData) {
            val (row, col) = getRowColForPalace(palace)
            magicSquareData[row][col] = data
        }
    }

    private fun calculateTrungCung(): MagicSquareCell {
        return MagicSquareCell(
            vanTinh = currentVan.toString(),
            sonTinh = currentToa.toString(),
            huongTinh = currentHuong.toString()
        )
    }

    private fun calculateCungCan(): MagicSquareCell {
        val vanTinh = phiTinhMotBuoc(currentVan, true)
        val sonTinh = phiTinhMotBuoc(currentToa, toaIsThuan)
        val huongTinh = phiTinhMotBuoc(currentHuong, huongIsThuan)
        return MagicSquareCell(vanTinh, sonTinh, huongTinh)
    }

    private fun calculateCungDoai(): MagicSquareCell {
        val vanTinh = phiTinhNhieuBuoc(currentVan, true, 2)
        val sonTinh = phiTinhNhieuBuoc(currentToa, toaIsThuan, 2)
        val huongTinh = phiTinhNhieuBuoc(currentHuong, huongIsThuan, 2)
        return MagicSquareCell(vanTinh, sonTinh, huongTinh)
    }

    private fun calculateCungCanCung(): MagicSquareCell {
        val vanTinh = phiTinhNhieuBuoc(currentVan, true, 3)
        val sonTinh = phiTinhNhieuBuoc(currentToa, toaIsThuan, 3)
        val huongTinh = phiTinhNhieuBuoc(currentHuong, huongIsThuan, 3)
        return MagicSquareCell(vanTinh, sonTinh, huongTinh)
    }

    private fun calculateCungLy(): MagicSquareCell {
        val vanTinh = phiTinhNhieuBuoc(currentVan, true, 4)
        val sonTinh = phiTinhNhieuBuoc(currentToa, toaIsThuan, 4)
        val huongTinh = phiTinhNhieuBuoc(currentHuong, huongIsThuan, 4)
        return MagicSquareCell(vanTinh, sonTinh, huongTinh)
    }

    private fun calculateCungKham(): MagicSquareCell {
        val vanTinh = phiTinhNhieuBuoc(currentVan, true, 5)
        val sonTinh = phiTinhNhieuBuoc(currentToa, toaIsThuan, 5)
        val huongTinh = phiTinhNhieuBuoc(currentHuong, huongIsThuan, 5)
        return MagicSquareCell(vanTinh, sonTinh, huongTinh)
    }

    private fun calculateCungKhon(): MagicSquareCell {
        val vanTinh = phiTinhNhieuBuoc(currentVan, true, 6)
        val sonTinh = phiTinhNhieuBuoc(currentToa, toaIsThuan, 6)
        val huongTinh = phiTinhNhieuBuoc(currentHuong, huongIsThuan, 6)
        return MagicSquareCell(vanTinh, sonTinh, huongTinh)
    }

    private fun calculateCungChan(): MagicSquareCell {
        val vanTinh = phiTinhNhieuBuoc(currentVan, true, 7)
        val sonTinh = phiTinhNhieuBuoc(currentToa, toaIsThuan, 7)
        val huongTinh = phiTinhNhieuBuoc(currentHuong, huongIsThuan, 7)
        return MagicSquareCell(vanTinh, sonTinh, huongTinh)
    }

    private fun calculateCungTon(): MagicSquareCell {
        val vanTinh = phiTinhNhieuBuoc(currentVan, true, 8)
        val sonTinh = phiTinhNhieuBuoc(currentToa, toaIsThuan, 8)
        val huongTinh = phiTinhNhieuBuoc(currentHuong, huongIsThuan, 8)
        return MagicSquareCell(vanTinh, sonTinh, huongTinh)
    }

    private fun phiTinhMotBuoc(saoGoc: Int, isThuan: Boolean): String {
        var saoHienTai = saoGoc
        saoHienTai = if (isThuan) {
            if (saoHienTai == 9) 1 else saoHienTai + 1
        } else {
            if (saoHienTai == 1) 9 else saoHienTai - 1
        }
        return saoHienTai.toString()
    }

    private fun phiTinhNhieuBuoc(saoGoc: Int, isThuan: Boolean, steps: Int): String {
        var saoHienTai = saoGoc
        repeat(steps) {
            saoHienTai = if (isThuan) {
                if (saoHienTai == 9) 1 else saoHienTai + 1
            } else {
                if (saoHienTai == 1) 9 else saoHienTai - 1
            }
        }
        return saoHienTai.toString()
    }

    private fun getRowColForPalace(palaceNumber: Int): Pair<Int, Int> {
        return when (palaceNumber) {
            4 -> Pair(0, 0); 9 -> Pair(0, 1); 2 -> Pair(0, 2)
            3 -> Pair(1, 0); 5 -> Pair(1, 1); 7 -> Pair(1, 2)
            8 -> Pair(2, 0); 1 -> Pair(2, 1); 6 -> Pair(2, 2)
            else -> Pair(1, 1)
        }
    }

    private fun getColorForNumber(number: String): Int {
        val num = number.toIntOrNull() ?: 0
        return when (num) {
            3, 4 -> ContextCompat.getColor(context, R.color.lsmoc)
            9 -> ContextCompat.getColor(context, R.color.lsred)
            2, 5, 8 -> ContextCompat.getColor(context, R.color.lstho)
            6, 7 -> ContextCompat.getColor(context, R.color.lskim)
            1 -> ContextCompat.getColor(context, R.color.lsblack)
            else -> Color.WHITE
        }
    }
}
