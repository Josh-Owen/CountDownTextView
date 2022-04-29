package com.joshowen.countdowntextview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CountDownTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    //region Constructor
    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CountDownTextView, 0, 0).apply {
            try {

                val isPulsationEnabled = getBoolean(R.styleable.CountDownTextView_pulsatingAnimationEnabled, DEFAULT_IS_PULSATION_ENABLED)
                val startValue = getInteger(R.styleable.CountDownTextView_startTimerValue, DEFAULT_START_VALUE)
                val endValue = getInteger(R.styleable.CountDownTextView_endTimerValue, DEFAULT_END_VALUE)

                if (startValue < endValue) throw IllegalStateException("The timer start value must be greater than the end value. Current: Start=$startValue & End=$endValue")
                

            } finally {
                recycle()
            }
        }
    }
}