package com.example.myapplication

import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

const val PROGRESS_START_ANGLE = 270
const val PROGRESS_BACKGROUND_START_ANGLE = 0
const val PROGRESS_BACKGROUND_END_ANGLE = 360
const val PROGRESS_STROKE_WIDTH = 24
const val BLOB_WIDTH = 60
const val PROGRESS_BACKGROUND_COLOR = "#e0e0e0"
const val PROGRESS_COLOR = "#00E676"
const val BLOB_COLOR = "#f3c623"
const val ANIMATION_TIME = 1_000
const val RADIUS = 0f
const val MAX_PROGRESS = 100.0
const val PROGRESS = 0.0

class CircularProgressBar(context: Context, attrs: AttributeSet?) : View(context , attrs) {
    private var progressPaint: Paint
    private var progressBackgroundPaint: Paint
    private var blobPaint: Paint
    private var startAngle = PROGRESS_START_ANGLE
    private var backgroundStartAngle = PROGRESS_BACKGROUND_START_ANGLE
    private var backgroundEndAngle = PROGRESS_BACKGROUND_END_ANGLE
    private var progressCircleLimit: RectF? = null
    private var circleRadius = RADIUS
    private var maxProgress = MAX_PROGRESS
    private var progress = PROGRESS
    private var progressBarAnimation: ValueAnimator? = null

    init {
        blobPaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            color = Color.parseColor(BLOB_COLOR)
            strokeCap = Paint.Cap.ROUND
            strokeWidth = BLOB_WIDTH.toFloat()
        }

        progressPaint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.parseColor(PROGRESS_COLOR)
            strokeCap = Paint.Cap.ROUND
            strokeWidth = PROGRESS_STROKE_WIDTH.toFloat()
        }

        progressBackgroundPaint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.parseColor(PROGRESS_BACKGROUND_COLOR)
            strokeWidth = PROGRESS_STROKE_WIDTH.toFloat()
        }

        progressCircleLimit = RectF()
    }

    fun setCircularProgressBar(progressValue: Double) {
        val newAngle: Double = progressValue / maxProgress * 360
        val previousProgress = progress
        progress = Math.min(progressValue, maxProgress)
        startProgressAnimation(previousProgress, newAngle)
    }

    private fun startProgressAnimation(previousProgress: Double, newAngle: Double) {
        val angle = PropertyValuesHolder.ofInt("angle", backgroundStartAngle, newAngle.toInt())
        progressBarAnimation = ValueAnimator.ofObject(
            TypeEvaluator<Double> { fractionValue, startValue, endValue ->
                startValue + (endValue - startValue) * fractionValue
            },
            previousProgress, progress
        )
            .apply {
                duration = ANIMATION_TIME.toLong()
                interpolator = interpolator
                setValues(angle)
                addUpdateListener { animation ->
                    backgroundStartAngle = animation.getAnimatedValue("angle") as Int
                    invalidate()
                }
                start()
            }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        circleRadius = w / 2f
        val blobWidth = blobPaint.strokeWidth
        val progressWidth = progressPaint.strokeWidth
        val progressBackgroundWidth = progressBackgroundPaint.strokeWidth
        val strokeSize = Math.max(blobWidth, Math.max(progressWidth, progressBackgroundWidth))
        val halfStrokeSize = strokeSize / 2f
        progressCircleLimit!!.left = halfStrokeSize
        progressCircleLimit!!.top = halfStrokeSize
        progressCircleLimit!!.right = w - halfStrokeSize
        progressCircleLimit!!.bottom = h - halfStrokeSize
        circleRadius = progressCircleLimit!!.width() / 2f
    }

    override fun onDraw(canvas: Canvas) {
        val angleRadians = Math.toRadians(startAngle + backgroundStartAngle + 180.toDouble())
        val cos = Math.cos(angleRadians).toFloat()
        val sin = Math.sin(angleRadians).toFloat()
        val x = progressCircleLimit!!.centerX() - circleRadius * cos
        val y = progressCircleLimit!!.centerY() - circleRadius * sin

        //gray background circle
        canvas.drawArc(
            progressCircleLimit!!,
            backgroundStartAngle.toFloat(),
            backgroundEndAngle.toFloat(),
            false,
            progressBackgroundPaint
        )

        //green progress arc
        canvas.drawArc(
            progressCircleLimit!!,
            startAngle.toFloat(),
            backgroundStartAngle.toFloat(),
            false,
            progressPaint
        )

        //yellow blob at the end of progress
        canvas.drawPoint(x, y, blobPaint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (progressBarAnimation != null) {
            progressBarAnimation!!.cancel()
        }
    }

}