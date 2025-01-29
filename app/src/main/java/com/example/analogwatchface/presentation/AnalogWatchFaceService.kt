package com.example.analogwatchface.presentation

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasWatchFaceService // Correct import
import androidx.wear.watchface.WatchFaceStyle // Correct import
import java.util.Calendar

class AnalogWatchFaceService : CanvasWatchFaceService() {

    override fun onCreateEngine(): Engine {
        return AnalogEngine()
    }

    private inner class AnalogEngine : Engine() {

        private val hourPaint = Paint().apply {
            color = Color.WHITE
            strokeWidth = 12f
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
        }

        private val minutePaint = Paint().apply {
            color = Color.WHITE
            strokeWidth = 8f
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
        }

        private val secondPaint = Paint().apply {
            color = Color.RED
            strokeWidth = 4f
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
        }

        private val calendar = Calendar.getInstance()

        override fun onCreate(holder: SurfaceHolder) {
            super.onCreate(holder)
            setWatchFaceStyle(
                WatchFaceStyle.Builder(this@AnalogWatchFaceService)
                    .setAcceptsTapEvents(false) // Disable tap events for simplicity
                    .build()
            )
        }

        override fun onDraw(canvas: Canvas, bounds: Rect) {
            // Draw background
            canvas.drawColor(Color.BLACK)

            // Get current time
            calendar.timeInMillis = System.currentTimeMillis()
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)
            val second = calendar.get(Calendar.SECOND)

            // Calculate angles
            val hourAngle = (hour + minute / 60f) * 30f
            val minuteAngle = (minute + second / 60f) * 6f
            val secondAngle = second * 6f

            // Draw clock hands
            drawHand(canvas, bounds, hourAngle, hourPaint, 0.4f) // Hour hand
            drawHand(canvas, bounds, minuteAngle, minutePaint, 0.6f) // Minute hand
            drawHand(canvas, bounds, secondAngle, secondPaint, 0.8f) // Second hand
        }

        private fun drawHand(canvas: Canvas, bounds: Rect, angle: Float, paint: Paint, length: Float) {
            val centerX = bounds.width() / 2f
            val centerY = bounds.height() / 2f
            val radians = Math.toRadians(angle.toDouble())
            canvas.drawLine(
                centerX, centerY,
                centerX + centerX * length * Math.sin(radians).toFloat(),
                centerY - centerY * length * Math.cos(radians).toFloat(),
                paint
            )
        }

        override fun onTimeTick() {
            super.onTimeTick()
            invalidate() // Redraw the watch face
        }
    }
}