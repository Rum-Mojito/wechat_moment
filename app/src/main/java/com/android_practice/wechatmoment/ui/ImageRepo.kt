package com.android_practice.wechatmoment.ui

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.arch.core.executor.ArchTaskExecutor
import com.android_practice.wechatmoment.model.Image
import com.android_practice.wechatmoment.ui.Utils.makeBitmapSquare
import java.io.File


class ImageRepo(
    diskCacheDir: File
) {
    private val memoryCache = MemoryCache()
    private val diskCache = DiskCache(diskCacheDir)

    @SuppressLint("RestrictedApi")
    fun getImage(imageView: ImageView, imageItem: Image, imageWidth: Int, imageHeight: Int) {

        if (memoryCache.get(imageItem.url) == null) {
            diskCache.get(imageItem.url) {
                if (it == null) {
                    ArchTaskExecutor.getIOThreadExecutor().execute {
                        val downloadResultTemp =
                            Utils.downloadBitmapFromURL(imageItem.url, imageWidth, imageHeight)
                        val downloadResult = downloadResultTemp?.let { downloadBitmap ->
                            makeBitmapSquare(downloadBitmap)
                        }
                        downloadResult?.let { downloadBitmap ->
                            memoryCache.set(imageItem.url, downloadBitmap)
                            diskCache.set(imageItem.url, downloadBitmap)
                            ArchTaskExecutor.getMainThreadExecutor().execute {
                                imageView.setImageBitmap(downloadBitmap)
                            }

                        }
                    }
                } else {
                    ArchTaskExecutor.getMainThreadExecutor().execute {
                        memoryCache.set(imageItem.url, it)
                        imageView.setImageBitmap(it)
                    }

                }
            }
        }else{
            imageView.setImageBitmap(memoryCache.get(imageItem.url))
        }
    }

    fun close() = diskCache.close()
}