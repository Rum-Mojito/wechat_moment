package com.android_practice.wechatmoment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = MomentItem::class,
        parentColumns = ["mid"],
        childColumns = ["momentId"],
        onUpdate = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val cid: Int?,
    val momentId: Long?,
    val content: String,
    @Embedded
    val sender: Sender
)