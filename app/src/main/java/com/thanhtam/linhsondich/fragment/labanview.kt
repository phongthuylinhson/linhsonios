package com.thanhtam.linhsondich.fragment

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class LaBanView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Danh sách 24 sơn vị
    private val sonViList = listOf(
        "Nhâm", "Tý", "Quý", "Sửu", "Cấn", "Dần", "Giáp", "Mão",
        "Ất", "Thìn", "Tốn", "Tỵ", "Bính", "Ngọ", "Đinh", "Mùi",
        "Khôn", "Thân", "Canh", "Dậu", "Tân", "Tuất", "Càn", "Hợi"
    )

    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f

    // Định nghĩa các loại Paint để vẽ
    private val paintCircle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 3f
    }

    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
        textSize = 30f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    private val paintLine = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        strokeWidth = 2f
    }

    private val paintMainLine = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        strokeWidth = 4f
    }

    private val paintThickLineForTick = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        strokeWidth = 4f
    }
    private val paintMediumLineForTick = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        strokeWidth = 3f
    }
    private val paintSmallLineForTick = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        strokeWidth = 1.5f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val viewSize = min(w, h)
        centerX = w / 2f
        centerY = h / 2f
        radius = (viewSize / 2f) * 0.98f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawDegreeRing(canvas)
        drawSonViRing(canvas)
    }

    private fun drawDegreeRing(canvas: Canvas) {
        val degreeRadius = radius
        val textRadius = degreeRadius * 0.92f
        val lineStartRadius = degreeRadius * 0.98f
        val lineEndRadius = degreeRadius

        canvas.drawCircle(centerX, centerY, degreeRadius, paintCircle)

        for (i in 0 until 360) {
            val angleRad = Math.toRadians(i.toDouble() - 90)
            val cosAngle = cos(angleRad).toFloat()
            val sinAngle = sin(angleRad).toFloat()

            val isMainTick = i % 10 == 0
            val isHalfTick = i % 5 == 0

            val currentPaint = when {
                i % 90 == 0 -> paintMainLine
                isMainTick -> paintThickLineForTick
                isHalfTick -> paintMediumLineForTick
                else -> paintSmallLineForTick
            }

            val startX = centerX + lineStartRadius * (if (isMainTick) 0.96f else 0.98f) * cosAngle
            val startY = centerY + lineStartRadius * (if (isMainTick) 0.96f else 0.98f) * sinAngle
            val endX = centerX + lineEndRadius * cosAngle
            val endY = centerY + lineEndRadius * sinAngle
            canvas.drawLine(startX, startY, endX, endY, currentPaint)

            if (isMainTick) {
                val textX = centerX + textRadius * cosAngle
                val textY = centerY + textRadius * sinAngle + paintText.textSize / 3
                canvas.save()
                canvas.rotate(i.toFloat(), textX, textY)
                canvas.drawText(i.toString(), textX, textY, paintText)
                canvas.restore()
            }
        }
    }

    private fun drawSonViRing(canvas: Canvas) {
        val sonViOuterRadius = radius * 0.85f
        val sonViInnerRadius = radius * 0.60f
        val textRadius = (sonViOuterRadius + sonViInnerRadius) / 2

        canvas.drawCircle(centerX, centerY, sonViOuterRadius, paintCircle)
        canvas.drawCircle(centerX, centerY, sonViInnerRadius, paintCircle)

        val angleStep = 15f
        val startAngleOffset = -90f - 7.5f

        for (i in sonViList.indices) {
            val angleDeg = i * angleStep
            // === SỬA LỖI 1: Chuyển sang Double trước khi gọi toRadians ===
            val angleRad =
                Math.toRadians((angleDeg + startAngleOffset + angleStep).toDouble()).toFloat()

            val startX = centerX + sonViInnerRadius * cos(angleRad)
            val startY = centerY + sonViInnerRadius * sin(angleRad)
            val endX = centerX + sonViOuterRadius * cos(angleRad)
            val endY = centerY + sonViOuterRadius * sin(angleRad)
            canvas.drawLine(startX, startY, endX, endY, paintCircle)

            val textAngleDeg = angleDeg + (angleStep / 2)
            // === SỬA LỖI 2: Chuyển sang Double trước khi gọi toRadians ===
            val textAngleRad =
                Math.toRadians((textAngleDeg + startAngleOffset + angleStep).toDouble()).toFloat()
            val textX = centerX + textRadius * cos(textAngleRad)
            val textY = centerY + textRadius * sin(textAngleRad) + paintText.textSize / 3

            canvas.save()
            canvas.rotate(textAngleDeg + angleStep, textX, textY)
            canvas.drawText(sonViList[i], textX, textY, paintText)
            canvas.restore()
        }
    }
}
