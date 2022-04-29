package com.joshowen.countdowntextview

interface CountDownCallback {
    fun onPause() {}
    fun onResume() {}
    fun onStart() {}
    fun onStop() {}
    fun onTick(time : Int) {}
    fun onFinished()
}