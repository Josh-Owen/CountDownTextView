package com.joshowen.countdowntextview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvCountDown = findViewById<CountDownTextView>(R.id.countdown)
        val btnResume  = findViewById<Button>(R.id.btnStart)
        val btnPause = findViewById<Button>(R.id.btnPause)
        val btnStop = findViewById<Button>(R.id.btnStop)

        btnResume.setOnClickListener {
            tvCountDown.resume()
        }

        btnPause.setOnClickListener {
            tvCountDown.pause()
        }

        btnStop.setOnClickListener {
            tvCountDown.stop()
        }

        tvCountDown.start(
            object : CountDownCallback {

                override fun onFinished() {
                    Log.e("CountDown: " ,"onFinished")
                }

                override fun onPause() {
                    super.onPause()
                    Log.e("CountDown: " ,"onPause")
                }

                override fun onResume() {
                    super.onResume()
                    Log.e("CountDown: " ,"onResume")
                }

                override fun onStart() {
                    super.onStart()
                    Log.e("CountDown: " ,"onStart")
                }

                override fun onStop() {
                    super.onStop()
                    Log.e("CountDown: " ,"onStart")
                }

                override fun onTick(time: Int) {
                    super.onTick(time)
                    Log.e("CountDown: " , "onTick: $time")
                }
            }

        )



    }

}