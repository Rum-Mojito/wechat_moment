package com.android_practice.wechatmoment.model

import com.google.gson.annotations.SerializedName

data class MomentItemResponse(

    @SerializedName("comments")
    val commentResponses: List<CommentResponse>?,
    val content: String?,
    val images: List<Image>?,
    val sender: Sender?
)


