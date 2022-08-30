package com.udacity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f

    }


    private var progress: Int = 0

    private var animator = ValueAnimator()

    private var state1: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Completed -> {
                animator.cancel()
                invalidate()
                requestLayout()
            }

            ButtonState.Clicked -> {
                animator.start()
                invalidate()
                requestLayout()
            }

            ButtonState.Loading -> {
                animator.start()
                invalidate()
                requestLayout()
            }

        }
    }


    @JvmName("setButtonState1")
    fun setButtonState(state: ButtonState){
        state1 = state
    }


    init {
        isClickable = true



        animator = ValueAnimator.ofInt(0, 100).apply {
            duration = 2000
            interpolator = LinearInterpolator()
            repeatCount = -1
            repeatMode = ObjectAnimator.RESTART
            addUpdateListener {
                progress = this.animatedValue as Int
                invalidate()
                requestLayout()
            }
        }

        state1 = ButtonState.Completed
    }





    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        when (state1) {

            ButtonState.Clicked -> {
                paint.color = context.getColor(R.color.colorPrimary)
                canvas?.drawRect(Rect(0,0,widthSize,heightSize),paint)
                paint.color = context.getColor(R.color.colorPrimaryDark)
                canvas?.drawRect(0f,0f,widthSize * (progress.toDouble() / 100).toFloat(),heightSize.toFloat(),paint)
                paint.color = context.getColor(R.color.colorAccent)
                canvas?.drawArc(widthSize - 100.toFloat(),
                    10.0f, widthSize - 30.toFloat(),
                    heightSize - 50.toFloat(),
                    360f,
                    (360f * progress.toDouble() / 100).toFloat(),
                    true,
                    paint)


                paint.color =context.getColor(R.color.white)
                canvas?.drawText(context.getString(R.string.button_loading),(widthSize/2).toFloat()
                    ,(heightSize / 2)+15.toFloat(),paint)

            }

            ButtonState.Completed -> {
                paint.color = context.getColor(R.color.colorPrimary)
                canvas?.drawRect(Rect(0,0,widthSize,heightSize),paint)
                paint.color =context.getColor(R.color.white)
                canvas?.drawText(context.getString(R.string.button_name),(widthSize/2).toFloat(),
                    (heightSize / 2)+15.toFloat(),paint)
            }


            ButtonState.Loading -> {
                paint.color = context.getColor(R.color.colorPrimary)
                canvas?.drawRect(Rect(0,0,widthSize,heightSize),paint)
                paint.color = context.getColor(R.color.colorPrimaryDark)
                canvas?.drawRect(0f,0f,widthSize * (progress.toDouble() / 100).toFloat(),heightSize.toFloat(),paint)
                paint.color = context.getColor(R.color.colorAccent)
                canvas?.drawArc(widthSize - 100.toFloat(),
                    10.0f, widthSize - 30.toFloat(),
                    heightSize - 50.toFloat(),
                    360f,
                    (360f * progress.toDouble() / 100).toFloat(),
                    true,
                    paint)


                paint.color =context.getColor(R.color.white)
                canvas?.drawText(context.getString(R.string.button_loading),(widthSize/2).toFloat(),
                    (heightSize / 2)+15 .toFloat(),paint)

            }


        }





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
///////////////////////////////////////////////////////////////
