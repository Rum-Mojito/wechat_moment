package com.android_practice.wechatmoment.service

import androidx.lifecycle.LiveData
import com.android_practice.wechatmoment.model.MomentItemResponse
import retrofit2.http.GET

interface MomentsService {
    @GET("user/jsmith/tweets")
    fun getMomentsInfo(): LiveData<ApiResponse<List<MomentItemResponse>>>
}