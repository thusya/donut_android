package com.thusee.donutscore.views.score.customview

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.thusee.donutscore.R

/**
 * This object will hold the circular view in the center of the scene
 */
class ScoreCircleView(context: Context?, attrs: AttributeSet?): View(context, attrs) {

    companion object {
        const val ARC_FULL_ROTATION_DEGREE = 360
        const val PERCENTAGE_DIVIDER = 100.0
        const val PERCENTAGE_VALUE_HOLDER = "percentage"
    }

    private var verticalCenter: Float = 0.0f
    private var horizontalCenter: Float = 0.0f

    //This object will hold the circular view in the center of the scene
    private val parentViewSpace = RectF()
    private val progressViewSpace = RectF()
    private val texViewSpace = RectF()

    private val borderCircleSize = 250

    var currentScore: String = ""
    var totalScore: String = ""

    private var parentArcColor = context?.resources?.getColor(R.color.blue, null) ?: Color.BLUE
    private var progressArcColor = context?.resources?.getColor(R.color.red, null) ?: Color.RED
    private var currentPercentage = 0

    init {
        context?.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.ScoreCircleView,
            0, 0
        )?.apply {
            try {
                progressArcColor =
                    getColor(R.styleable.ScoreCircleView_percentageBarColor, Color.RED)
                parentArcColor = getColor(R.styleable.ScoreCircleView_borderColor, Color.RED)
            } finally {
                recycle()
            }
        }
    }

    private val parentArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = parentArcColor
        strokeWidth = 10f
    }

    private val percentArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = progressArcColor
        strokeWidth = 10f
        strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        setViewSpace()
        setProgressViewSpace()
        setTextViewSpace()
        canvas?.let {
            drawBackgroundArc(it)
            drawProgressArc(it)
            setStatusText(it)
        }
    }

    private fun setTextViewSpace() {
        val textSize = 175

        texViewSpace.set(
            horizontalCenter - textSize,
            verticalCenter - textSize,
            horizontalCenter + textSize,
            verticalCenter + textSize
        )
    }

    private fun drawBackgroundArc(it: Canvas) {
        it.drawArc(parentViewSpace, 0f, 360f, false, parentArcPaint)
    }

    private fun drawProgressArc(canvas: Canvas) {
        val percentageToFill = getCurrentPercentageToFill()
        canvas.drawArc(progressViewSpace, 270f, percentageToFill, false, percentArcPaint)
    }

    private fun setViewSpace() {

        parentViewSpace.set(
            horizontalCenter - borderCircleSize,
            verticalCenter - borderCircleSize,
            horizontalCenter + borderCircleSize,
            verticalCenter + borderCircleSize
        )
    }

    private fun setProgressViewSpace() {
        val progressBarCircleSize = borderCircleSize - 15

        progressViewSpace.set(
            horizontalCenter - progressBarCircleSize,
            verticalCenter - progressBarCircleSize,
            horizontalCenter + progressBarCircleSize,
            verticalCenter + progressBarCircleSize
        )
    }

    private fun getCurrentPercentageToFill() =
        (ARC_FULL_ROTATION_DEGREE * (currentPercentage / PERCENTAGE_DIVIDER)).toFloat()

    fun setPercentageStatus(percentage: Float, currentScore: String, totalScore: String) {
        this.currentScore = currentScore
        this.totalScore = totalScore
        val valuesHolder = PropertyValuesHolder.ofFloat("percentage", 0f, percentage)
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            duration = 1000
            addUpdateListener {
                val percent = it.getAnimatedValue(PERCENTAGE_VALUE_HOLDER) as Float
                currentPercentage = percent.toInt()
                invalidate()
            }
        }
        animator.start()
    }

    private fun setStatusText(canvas: Canvas) {
        val scorePaint = Paint()
        scorePaint.textAlign = Paint.Align.CENTER
        scorePaint.textSize = 100f
        scorePaint.color = progressArcColor
        scorePaint.isAntiAlias = true

        val labelPaint = Paint()
        labelPaint.textAlign = Paint.Align.CENTER
        labelPaint.textSize = 40f
        labelPaint.isAntiAlias = true

        val xPos = canvas.width / 2
        val yPos = (canvas.height / 2 - (scorePaint.descent() + scorePaint.ascent()) / 2).toInt()

        if (currentScore.isNotEmpty()) {
            canvas.drawText(
                context.resources.getString(R.string.your_credit_score),
                xPos.toFloat(),
                yPos.toFloat() - 100,
                labelPaint
            )
            canvas.drawText(
                "${context.resources.getString(R.string.out_of)} $totalScore",
                xPos.toFloat(),
                yPos.toFloat() + 50,
                labelPaint
            )
        }

        canvas.drawText(currentScore, xPos.toFloat(), yPos.toFloat(), scorePaint)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        horizontalCenter = (width.div(2)).toFloat()
        verticalCenter = (height.div(2)).toFloat()
    }
}