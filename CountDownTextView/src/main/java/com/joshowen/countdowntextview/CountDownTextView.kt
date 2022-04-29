package com.joshowen.countdowntextview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CountDownTextView(context: Context, attrs: AttributeSet?) :
    AppCompatTextView(context, attrs) {

    //region Variables

    //region Count Down Values

    private var currentValue: Int = DEFAULT_START_VALUE

    private var startValue: Int = DEFAULT_START_VALUE

    private var endValue: Int = DEFAULT_END_VALUE

    private var isPulsatingEnabled = DEFAULT_IS_PULSATION_ENABLED

    //endregion

    //region State

    private var isPaused = false

    private var isStopped = false

    private var isPlayingScaleAnimation: Boolean = false

    //region Animations

    private var scaleAnimation = getTimerScaleAnimation()

    private var alphaAnimation = getTimerAlphaAnimation()

    //endregion

    //endregion

    //region Callbacks
    private var listener: CountDownCallback? = null

    //endregion

    //region Constructor
    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CountDownTextView, 0, 0).apply {
            try {

                startValue =
                    getInteger(R.styleable.CountDownTextView_startTimerValue, DEFAULT_START_VALUE)
                endValue =
                    getInteger(R.styleable.CountDownTextView_endTimerValue, DEFAULT_END_VALUE)
                isPulsatingEnabled = getBoolean(
                    R.styleable.CountDownTextView_pulsationEnabled,
                    DEFAULT_IS_PULSATION_ENABLED
                )

                if (startValue < endValue) throw IllegalStateException("The timer start value must be greater than the end value.\nCurrent Values: Start=$startValue & End=$endValue")

            } finally {
                recycle()
            }
        }
    }
    //endregion

    //region Timer Functions

    fun start(callback: CountDownCallback) {
        listener = callback
        currentValue = startValue
        countDown()
        listener?.onStart()
    }

    fun stop() {
        isStopped = true
        listener?.onStop()
    }

    fun pause() {
        isPaused = true
        listener?.onPause()
    }

    fun resume() {
        isPaused = false
        countDown()
        listener?.onResume()
    }
    //endregion

    //region Schedulers
    private fun scheduleCountDown(delay: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            countDown()
        }, delay)
    }
    //endregion

    //region Countdown Logic
    private fun countDown() {
        if (!isPaused && !isStopped) {
            when {
                isPlayingScaleAnimation -> {
                    if (isPulsatingEnabled) {
                        startAnimation(alphaAnimation)
                    }
                    scheduleCountDown(SCALE_ANIMATION_DURATION)
                }
                currentValue != endValue -> {
                    text = currentValue.toString()
                    listener?.onTick(currentValue)
                    if (isPulsatingEnabled) {
                        startAnimation(scaleAnimation)
                    }
                    currentValue -= 1
                    scheduleCountDown(ALPHA_ANIMATION_DURATION)
                }
                else -> {
                    listener?.onFinished()
                }
            }
            isPlayingScaleAnimation = !isPlayingScaleAnimation
        }
        //endregion
    }
}