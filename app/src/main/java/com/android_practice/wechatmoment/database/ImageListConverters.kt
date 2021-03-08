package com.android_practice.wechatmoment.database

import androidx.room.TypeConverter
import com.android_practice.wechatmoment.model.Image
import timber.log.Timber

class ImageListConverters {

    @TypeConverter
    fun stringToImageList(data: String?): List<Image>? {
        if (data == "") {
            return null
        }
        var imageList = ArrayList<Image>()
        data?.let {
            it.split(",").forEach {
                try {
                    imageList.add(Image(it.toString()))
                } catch (ex: NumberFormatException) {
                    Timber.e(ex, "Cannot convert $it to Image")
                }
            }
        }
        return imageList
    }

    @TypeConverter
    fun imageListToString(images: List<Image>?): String? {
        var result = ""
        images?.forEach { result += it.url + "," }
        return result.dropLastWhile { !it.isLetter() }
    }
}
