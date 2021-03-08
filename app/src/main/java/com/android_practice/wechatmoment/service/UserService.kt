package com.android_practice.wechatmoment.service

import androidx.lifecycle.LiveData
import com.android_practice.wechatmoment.model.User
import retrofit2.http.GET

interface UserService {
    @GET("user/jsmith/")
    fun getUserInfo(): LiveData<ApiResponse<User>>
}
