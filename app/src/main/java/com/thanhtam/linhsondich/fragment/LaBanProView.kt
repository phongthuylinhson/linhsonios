package com.thanhtam.linhsondich.fragment

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.thanhtam.linhsondich.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class LaBanProView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private data class SonVi(val name: String, val startDeg: Float, val endDeg: Float)

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

    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f
    private var arrowAngle: Float = 0f
    private var arrowExtension = 0f

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
        color = ContextCompat.getColor(context, R.color.lsred)
        strokeWidth = 5f
        strokeCap = Paint.Cap.ROUND
    }
    private val paintLsRedFill = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.lsred)
        style = Paint.Style.FILL
    }
    private val paintWhiteStroke = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }
    private val paintMagicSquare = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#7d7c7c")
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    // === BẮT ĐẦU SỬA LỖI ===
    // Di chuyển các khai báo ra khỏi khối apply
    private val paintBitmap = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bgBitmap: Bitmap? = null

    // Đặt khối init ở đúng cấp độ của class
    init {
        bgBitmap = BitmapFactory.decodeResource(resources, R.drawable.cuucung_background)
        arrowExtension = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 8f * 1.72f, resources.displayMetrics
        )
    }
    // === KẾT THÚC SỬA LỖI ===

    fun setDirection(degrees: Float) {
        arrowAngle = degrees
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val viewSize = min(w, h)
        centerX = w / 2f
        centerY = h / 2f
        radius = viewSize / 2f * 0.8f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // === SỬA LỖI THỨ TỰ VẼ CHUẨN XÁC ===

        // Lớp 1: Vẽ vòng độ số ngoài cùng (nền trắng)
        drawDegreeRing(canvas)

        // Lớp 2: Vẽ vòng 24 sơn (nền đỏ)
        drawSonViRing(canvas)

        // Lớp 3: Vẽ ảnh Cửu Cung lên trên nền trắng/đỏ, ở chính giữa
        drawCuuCungBackground(canvas)

        // Lớp 4: Vẽ Ma phương lên trên ảnh Cửu Cung
        drawMagicSquare(canvas)

        // Lớp 5: Vẽ mũi tên trên cùng
        drawArrow(canvas)
    }


    private fun drawCuuCungBackground(canvas: Canvas) {
        bgBitmap?.let { bmp ->
            // Chỉ vẽ ảnh nền vào khu vực bên trong vòng 24 sơn
            val imageRadius = radius * 0.7f // Bằng với bán kính trong của vòng 24 sơn
            val destRect = RectF(
                centerX - imageRadius,
                centerY - imageRadius,
                centerX + imageRadius,
                centerY + imageRadius
            )

            // Giữ nguyên độ mờ bạn đã chọn
            paintBitmap.alpha = 54
            canvas.drawBitmap(bmp, null, destRect, paintBitmap)
            paintBitmap.alpha = 255 // Khôi phục lại alpha
        }
    }


    private fun drawDegreeRing(canvas: Canvas) {
        // Thu nhỏ vòng ngoài để có không gian cho chữ
        val outerRadius = radius * 0.95f
        val innerRadius = radius * 0.85f

        // 1. Đưa text ra ngoài vòng tròn
        val textPadding = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics
        )
        val textRadius = outerRadius + textPadding + paintDegreeText.textSize / 2

        // Tạo paint tô nền trắng cục bộ, không sửa paint chung
        val whiteFillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.WHITE
        }

        // Vẽ nền trắng cho vòng độ số
        val path = Path().apply {
            addCircle(centerX, centerY, outerRadius, Path.Direction.CW)
            addCircle(centerX, centerY, innerRadius, Path.Direction.CCW)
        }
        canvas.drawPath(path, whiteFillPaint)

        // 2. Vẽ viền và vạch chính bằng paintLine gốc (màu #f2eeed)
        canvas.drawCircle(centerX, centerY, outerRadius, paintLine)
        canvas.drawCircle(centerX, centerY, innerRadius, paintLine)

        for (i in 0 until 360 step 5) {
            val isTenMultiple = i % 10 == 0
            val angleRad = Math.toRadians(i.toDouble() - 90)
            val cosA = cos(angleRad).toFloat()
            val sinA = sin(angleRad).toFloat()

            // Giữ nguyên logic vạch dài/ngắn của bạn
            val lineStart = if (isTenMultiple) innerRadius * 1.02f else innerRadius * 1.05f
            canvas.drawLine(
                centerX + lineStart * cosA, centerY + lineStart * sinA,
                centerX + outerRadius * cosA, centerY + outerRadius * sinA,
                if (isTenMultiple) paintLine else paintTickLine // Vạch chính dùng #f2eeed, vạch phụ dùng GRAY
            )

            if (isTenMultiple) {
                val text = i.toString()
                // Sử dụng textRadius mới để tính toán vị trí X, Y
                val x = centerX + textRadius * cosA
                val y = centerY + textRadius * sinA + paintDegreeText.textSize / 3
                canvas.save()
                canvas.rotate(i.toFloat(), x, y)
                canvas.drawText(text, x, y, paintDegreeText)
                canvas.restore()
            }
        }
    }


    private fun drawSonViRing(canvas: Canvas) {
        val outerRadius = radius * 0.84f
        val innerRadius = radius * 0.7f
        val nameTextRadius = (outerRadius + innerRadius) / 2

        // Vẽ nền màu lsred
        val path = Path().apply {
            addCircle(centerX, centerY, outerRadius, Path.Direction.CW)
            addCircle(centerX, centerY, innerRadius, Path.Direction.CCW)
        }
        canvas.drawPath(path, paintLsRedFill)

        // Dùng màu trắng cho cả viền và vạch chia để nổi bật trên nền đỏ
        canvas.drawCircle(centerX, centerY, outerRadius, paintWhiteStroke)
        canvas.drawCircle(centerX, centerY, innerRadius, paintWhiteStroke)

        sonViList.forEach { sonVi ->
            val canvasAngle = sonVi.startDeg - 90
            val angleRad = Math.toRadians(canvasAngle.toDouble()).toFloat()

            // Vẽ vạch chia sơn bằng màu trắng
            canvas.drawLine(
                centerX + innerRadius * cos(angleRad), centerY + innerRadius * sin(angleRad),
                centerX + outerRadius * cos(angleRad), centerY + outerRadius * sin(angleRad),
                paintWhiteStroke
            )

            // === GIẢI PHÁP CANH GIỮA CUỐI CÙNG ===
            val midAngleDeg = (sonVi.startDeg + 7.5f) % 360

            canvas.save()
            // 1. Xoay toàn bộ canvas quanh TÂM của la bàn
            canvas.rotate(midAngleDeg, centerX, centerY)

            // 2. Tính toán vị trí Y để vẽ text
            // - Lấy điểm trên cùng của vòng (centerY - nameTextRadius)
            // - Trừ đi tâm Y của text để đưa nó vào giữa
            val textBounds = Rect()
            paintText.getTextBounds(sonVi.name, 0, sonVi.name.length, textBounds)
            // textBounds.exactCenterY() trả về tâm Y của chữ, ta trừ đi để đưa nó về đúng giữa
            val textY = centerY - nameTextRadius - textBounds.exactCenterY()

            // 3. Vẽ text tại centerX (vì textAlign là CENTER) và vị trí Y đã được tính toán
            canvas.drawText(sonVi.name, centerX, textY, paintText)

            // 4. Khôi phục canvas về trạng thái ban đầu cho lần vẽ tiếp theo
            canvas.restore()
            // =======================================
        }
    }


    private fun drawMagicSquare(canvas: Canvas) {
        val innerRadius = radius * 0.7f
        val boxSize = innerRadius * 2 * 0.7f // Giữ nguyên kích thước bạn đã chọn

        val startX = centerX - boxSize / 2
        val startY = centerY - boxSize / 2
        val endX = centerX + boxSize / 2
        val endY = centerY + boxSize / 2
        val cellSize = boxSize / 3

        // SỬA ĐỔI: Sử dụng paintMagicSquare chuyên dụng với màu #7d7c7c
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
    }


    private fun drawArrow(canvas: Canvas) {
        canvas.save()
        canvas.rotate(arrowAngle, centerX, centerY)
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
}
