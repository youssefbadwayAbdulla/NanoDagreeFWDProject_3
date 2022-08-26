package com.udacity

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates
class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var bgColor: Int = Color.RED
    private var textColor: Int = Color.RED
    private var animator = ValueAnimator()
    private var progress: Double = 0.0
    private var buttonState: ButtonState by Delegates.observable(ButtonState.Completed)
    { p, old, new ->

    }
    private val animatorUpdateListener = ValueAnimator.AnimatorUpdateListener {
        progress = (it.animatedValue as Float).toDouble()

        invalidate()
        requestLayout()
    }

    init {
        isClickable = true

        animator = AnimatorInflater.loadAnimator(
            context, R.animator.animator
        ) as ValueAnimator

        animator.addUpdateListener(animatorUpdateListener)

    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    override fun performClick(): Boolean {
        super.performClick()
        if (buttonState == ButtonState.Completed) buttonState = ButtonState.Loading
        animation()

        return true
    }

    private fun animation() {
        animator.start()
    }

    private val rectF = RectF(
        740f,
        50f,
        810f,
        110f
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.strokeWidth = 0f
        paint.color = bgColor
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)


        if (buttonState == ButtonState.Loading) {
            paint.color = Color.parseColor("#004349")
            canvas.drawRect(
                0f, 0f,
                (width * (progress / 100)).toFloat(), height.toFloat(), paint
            )
            paint.color = Color.parseColor("#F9A825")
            canvas.drawArc(rectF, 0f, (360 * (progress / 100)).toFloat(), true, paint)
        }
        val buttonText =
            if (buttonState == ButtonState.Loading)
                resources.getString(R.string.loading)
            else resources.getString(R.string.download)

        paint.color = textColor
        canvas.drawText(buttonText, (width / 2).toFloat(), ((height + 30) / 2).toFloat(),
            paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }
}