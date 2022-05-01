package com.joshowen.countdown_textview

interface CountDownCallback {
    fun onPause() {}
    fun onResume() {}
    fun onStart() {}
    fun onStop() {}
    fun onRestart() {}
    fun onTick(time : Int) {}
    fun onFinished()
}