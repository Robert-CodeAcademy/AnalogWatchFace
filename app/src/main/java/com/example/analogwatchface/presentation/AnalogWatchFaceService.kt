package com.example.analogwatchface

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.service.wallpaper.WallpaperService
import androidx.wear.watchface.CanvasWatchFaceService
import androidx.wear.watchface.style.UserStyleSchema
import java.util.Calendar

class AnalogWatchFaceService : CanvasWatchFaceService() {

    override fun onCreateEngine(): Engine {
        return WatchFaceEngine()
    }

    private inner class WatchFaceEngine : CanvasWatchFaceService.Engine() {
        private val backgroundPaint = Paint().apply { color = Color.BLACK }
        private val hourHandPaint = Paint().apply {
            color = Color.WHITE
            strokeWidth = 8f
            isAntiAlias = true
        }
        private val minuteHandPaint = Paint().apply {
            color = Color.LTGRAY
            strokeWidth = 5f
            isAntiAlias = true
        }
        private val secondHandPaint = Paint().apply {
            color = Color.RED
            strokeWidth = 3f
            isAntiAlias = true
        }
        private val calendar = Calendar.getInstance()

        override fun onDraw(canvas: Canvas, bounds: Rect) {
            val centerX = bounds.exactCenterX()
            val centerY = bounds.exactCenterY()
            val radius = (Math.min(bounds.width(), bounds.height()) / 2 * 0.8).toFloat()

            // Draw background
            canvas.drawColor(Color.BLACK)

            // Get current time
            calendar.timeInMillis = System.currentTimeMillis()

            // Draw hour hand
            val hourRotation = (calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE) / 60f) * 30f
            drawHand(canvas, centerX, centerY, hourRotation, radius * 0.5f, hourHandPaint)

            // Draw minute hand
            val minuteRotation = calendar.get(Calendar.MINUTE) * 6f
            drawHand(canvas, centerX, centerY, minuteRotation, radius * 0.7f, minuteHandPaint)

            // Draw second hand
            val secondRotation = calendar.get(Calendar.SECOND) * 6f
            drawHand(canvas, centerX, centerY, secondRotation, radius * 0.9f, secondHandPaint)
        }

        private fun drawHand(
            canvas: Canvas,
            centerX: Float,
            centerY: Float,
            rotation: Float,
            length: Float,
            paint: Paint
        ) {
            val angle = Math.toRadians(rotation.toDouble() - 90)
            val handX = (centerX + Math.cos(angle) * length).toFloat()
            val handY = (centerY + Math.sin(angle) * length).toFloat()
            canvas.drawLine(centerX, centerY, handX, handY, paint)
        }
    }
}
