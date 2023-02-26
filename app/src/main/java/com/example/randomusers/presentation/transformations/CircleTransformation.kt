package com.example.randomusers.presentation.transformations

import android.graphics.*
import androidx.annotation.ColorInt
import com.squareup.picasso.Transformation
import kotlin.math.min


class CircleTransformation(
    @ColorInt
    private val borderColor: Int, private val borderRadius: Float
) : Transformation {

    override fun transform(source: Bitmap?): Bitmap? {
        // if the source bitmap is null we can't do anything
        if (source == null) {
            return null
        }
        val size = min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }
        val bitmap = Bitmap.createBitmap(size, size, source.config)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader =
            BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true
        val r = size / 2f
        // if border is configured we draw it
        if (borderRadius != -1f) {
            // Prepare the background
            val paintBg = Paint()
            paintBg.color = borderColor
            paintBg.isAntiAlias = true
            // Draw the background circle
            canvas.drawCircle(r, r, r, paintBg)
            // Draw the image smaller than the background so a little border will be seen
            canvas.drawCircle(r, r, r - borderRadius, paint)
        } else {
            // Draw the image as a circle

            canvas.drawCircle(r, r, r, paint)
        }
        squaredBitmap.recycle()
        return bitmap
    }

    override fun key(): String {
        return "circle(color=$borderColor,radius=$borderRadius)"
    }
}