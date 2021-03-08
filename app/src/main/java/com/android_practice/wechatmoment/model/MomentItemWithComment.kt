package com.android_practice.wechatmoment.model

import androidx.room.Embedded
import androidx.room.Relation

data class MomentItemWithComment(

    @Relation(
        parentColumn = "mid",
        entityColumn = "momentId"
    )
    val comments: List<Comment>,
    @Embedded val momentItem: MomentItem

)