package com.joshowen.countdowntextview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CountDownTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    //region Variables

    //region Count Down Values

    private var currentValue: Int = DEFAULT_START_VALUE

    private var startValue: Int = DEFAULT_START_VALUE

    private var endValue: Int = DEFAULT_END_VALUE

    private var isPulsationEnabled = DEFAULT_IS_PULSATION_ENABLED

    //endregion

    //region State

    private var countDownState : CountDownState = CountDownState.IDLE

    private var isPlayingScaleAnimation: Boolean = false

    //endregion

    //region Handlers & Runnable

    private var countDownHandler = Handler(Looper.getMainLooper())

    private var updateRunnable = Runnable {
        countDown()
    }

    //endregion

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
                isPulsationEnabled = getBoolean(
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
        listener?.onStart()
        countDown()
    }

    fun stop() {
        countDownState = CountDownState.STOPPED
        listener?.onStop()
    }

    fun pause() {
        countDownState = CountDownState.PAUSED
        listener?.onPause()
    }

    fun resume() {
        countDownState = CountDownState.PLAYING
        listener?.onResume()
        countDown()
    }

    fun restart() {
        text = ""
        currentValue = startValue
        countDownHandler.removeCallbacks(updateRunnable)
        countDownState = CountDownState.PLAYING
        listener?.onRestart()
        countDown()
    }

    fun setEndTime(time : Int) {
        endValue = time
    }

    fun setStartTime(time : Int) {
        if(time > endValue) {
            currentValue = startValue
        }
    }

    fun enableOrDisableAnimation(isEnabled : Boolean) {
        isPulsationEnabled = isEnabled
    }

    //endregion

    //region Schedulers
    private fun scheduleCountDown(delay: Long) {
        countDownHandler.postDelayed(updateRunnable, delay)
    }
    //endregion

    //region Countdown Logic
    private fun countDown() {
        if (countDownState != CountDownState.PAUSED && countDownState != CountDownState.STOPPED && countDownState != CountDownState.FINISHED) {
            when {
                isPlayingScaleAnimation -> {
                    if (isPulsationEnabled) {
                        startAnimation(alphaAnimation)
                    }
                    scheduleCountDown(SCALE_ANIMATION_DURATION)
                }
                currentValue != endValue -> {
                    text = currentValue.toString()
                    listener?.onTick(currentValue)
                    if (isPulsationEnabled) {
                        startAnimation(scaleAnimation)
                    }
                    currentValue -= 1
                    scheduleCountDown(ALPHA_ANIMATION_DURATION)
                }
                else -> {
                    countDownState = CountDownState.FINISHED
                    listener?.onFinished()
                }
            }
            isPlayingScaleAnimation = !isPlayingScaleAnimation
        }
    }
    //endregion
}