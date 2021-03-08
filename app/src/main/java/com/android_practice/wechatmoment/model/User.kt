package com.android_practice.wechatmoment.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val avatar: String,
    val nick: String,
    @SerializedName("profile-image")
    val profileImage: String,
    val username: String
)