// AnalogWatchFaceService.kt
package com.example.analogwatchface

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasWatchFaceService
import androidx.wear.watchface.WatchFaceStyle
import java.util.Calendar

class AnalogWatchFaceService : CanvasWatchFaceService() {

    override fun onCreateEngine(): Engine {
        return WatchFaceEngine()
    }

    private inner class WatchFaceEngine : CanvasWatchFaceService.Engine() {
        private val backgroundPaint = Paint().apply {
            color = Color.BLACK
        }
        private val hourHandPaint = Paint().apply {
            color = Color.WHITE
            strokeWidth = 8f
            isAntiAlias = true
            style = Paint.Style.STROKE
        }
        private val minuteHandPaint = Paint().apply {
            color = Color.LTGRAY
            strokeWidth = 5f
            isAntiAlias = true
            style = Paint.Style.STROKE
        }
        private val secondHandPaint = Paint().apply {
            color = Color.RED
            strokeWidth = 3f
            isAntiAlias = true
            style = Paint.Style.STROKE
        }

        private val calendar: Calendar = Calendar.getInstance()

        override fun onDraw(canvas: Canvas, bounds: Rect) {
            val centerX = bounds.exactCenterX()
            val centerY = bounds.exactCenterY()
            val radius = (Math.min(bounds.width(), bounds.height()) / 2 * 0.8).toFloat()

            // Draw background
            canvas.drawRect(0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(), backgroundPaint)

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

        private fun drawHand(canvas: Canvas, centerX: Float, centerY: Float, rotation: Float, length: Float, paint: Paint) {
            val angle = Math.toRadians(rotation.toDouble() - 90)
            val handX = (centerX + Math.cos(angle) * length).toFloat()
            val handY = (centerY + Math.sin(angle) * length).toFloat()
            canvas.drawLine(centerX, centerY, handX, handY, paint)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            if (visible) invalidate()
        }

        override fun onSurfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            invalidate()
        }
    }
}
