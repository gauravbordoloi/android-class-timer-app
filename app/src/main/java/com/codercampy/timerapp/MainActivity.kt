package com.codercampy.timerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.codercampy.timerapp.databinding.ActivityMainBinding
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var maxTime: Int = 0 //seconds
    private var currentTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {

            val hr = binding.inputHour.editText?.text?.toString()?.trim()?.toIntOrNull()
            val min = binding.inputMinutes.editText?.text?.toString()?.trim()?.toIntOrNull()
            val seconds = binding.inputSeconds.editText?.text?.toString()?.trim()?.toIntOrNull()

            if (hr != null) {
                maxTime += hr * 60 * 60
            }
            if (min != null) {
                maxTime += min * 60
            }
            if (seconds != null) {
                maxTime += seconds
            }

            binding.viewClock.visibility = View.VISIBLE
            binding.viewInputs.visibility = View.GONE

            currentTime = maxTime
            binding.progress.max = maxTime
            binding.progress.
            min = 0
            binding.progress.progress = 0

            binding.tvCounter.text = parseTimeForReading()

            runHandler()
        }

        runHandler()

    }

    private fun runHandler() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (currentTime > 0) {
                binding.progress.progress = --currentTime
                binding.tvTime.text = parseTime()
                runHandler()
            }
        }, 1000)
    }

    private fun parseTimeForReading(): String {
        val seconds = maxTime % 60
        val minutes = maxTime / 60
        val hours = minutes / 60

        var s = ""
        if (hours > 0) {
            s += "${hours}h"
        }

        return buildString {
            if (hours > 0) {
                append(hours)
                append("h ")
            }
            if (minutes > 0) {
                append(minutes)
                append("m ")
            }
            if (seconds > 0) {
                append(seconds)
                append("s")
            }
        }
    }

    private fun parseTime(): String {
        val seconds = currentTime % 60
        val minutes = currentTime / 60
        val hours = minutes / 60

        return "$hours:$minutes:$seconds"
    }

}