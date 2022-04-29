package com.joshowen.countdowntextview

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

fun getTimerScaleAnimation() : ScaleAnimation {
    return ScaleAnimation(
        1f, 2f,
        1f, 2f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    ).apply {
        duration = ALPHA_ANIMATION_DURATION
    }
}

fun getTimerAlphaAnimation() : AlphaAnimation {
    return AlphaAnimation(1f, 0f)
        .apply {
            duration = SCALE_ANIMATION_DURATION
            fillAfter = true
        }
}