package com.android_practice.wechatmoment.ui


import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.os.Process
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.net.URL
import java.security.MessageDigest
import java.util.concurrent.ThreadFactory


object Utils {

    // Thread Factory to set Thread priority to Background
    internal class ImageThreadFactory : ThreadFactory {
        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable).apply {
                name = "ImageLoader Thread"
                priority = Process.THREAD_PRIORITY_BACKGROUND
            }
        }
    }

    fun makeBitmapSquare(bitmap: Bitmap): Bitmap? {

        var width: Int = bitmap.width
        var height: Int = bitmap.height
        if (width <= 0) width = 0
        if (height <= 0) height = 0

        var crop = (width - height) / 2
        if (crop <= 0) {
            return Bitmap.createBitmap(bitmap, 0, -crop, width, width)
        } else {
            return Bitmap.createBitmap(bitmap, crop, 0, height, height)
        }

    }

    fun downloadBitmapFromURL(imageUrl: String, imageWidth: Int, imageHeight: Int): Bitmap? {
        val url = URL(imageUrl)
        val inputStream = BufferedInputStream(url.openConnection().getInputStream())

        // Scale Bitmap to Screen Size to store in Cache
        return scaleBitmap(inputStream, imageWidth, imageHeight)
    }


    fun scaleBitmapForLoad(bitmap: Bitmap, width: Int, height: Int): Bitmap? {

        if (width == 0 || height == 0) return bitmap

        val stream = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.JPEG, 100, stream)
        val inputStream = BufferedInputStream(ByteArrayInputStream(stream.toByteArray()))

        // Scale Bitmap to required ImageView Size
        return scaleBitmap(inputStream, width, height)
    }

    private fun scaleBitmap(inputStream: BufferedInputStream, width: Int, height: Int): Bitmap? {
        return BitmapFactory.Options().run {
            inputStream.mark(inputStream.available())

            inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, this)

//            inSampleSize = calculateInSampleSize(this, width, height)
            inSampleSize = 1

            inJustDecodeBounds = false
            inputStream.reset()
            BitmapFactory.decodeStream(inputStream, null, this)
        }
    }

    // From Developer Site
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {

        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) inSampleSize *= 2
        }

        return inSampleSize
    }


    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }


}
