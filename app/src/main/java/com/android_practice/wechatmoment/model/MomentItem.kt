package com.android_practice.wechatmoment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.android_practice.wechatmoment.database.ImageListConverters

@Entity(tableName = "moment_item")
@TypeConverters(ImageListConverters::class)
data class MomentItem(
    @PrimaryKey(autoGenerate = true)
    val mid: Long?,
    val content: String?,
    val images: List<Image>?,
    @Embedded
    val sender: Sender?
)