package com.cindaku.nftar.utils

import android.graphics.*
import com.squareup.picasso.Transformation

class RoundedTransformation(
    private val radius: Int, // dp
    private val margin: Int
) : Transformation {


    override fun transform(source: Bitmap): Bitmap {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(
            source, Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        )
        val output: Bitmap = Bitmap.createBitmap(
            source.getWidth(),
            source.getHeight(), Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        canvas.drawRoundRect(
            RectF(
                margin.toFloat(), margin.toFloat(), (source.getWidth()
                        - margin).toFloat(), (source.getHeight() - margin).toFloat()
            ), radius.toFloat(), radius.toFloat(), paint
        )
        if (source !== output) {
            source.recycle()
        }
        return output
    }

    override fun key(): String {
        return "rounded"
    }
}