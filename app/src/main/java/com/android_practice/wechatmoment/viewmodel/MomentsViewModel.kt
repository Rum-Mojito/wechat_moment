package com.android_practice.wechatmoment.viewmodel

import androidx.lifecycle.ViewModel
import com.android_practice.wechatmoment.MainApplication
import com.android_practice.wechatmoment.database.WechatDatabase
import com.android_practice.wechatmoment.repository.MomentsRepo
import com.android_practice.wechatmoment.service.AppExecutors
import com.android_practice.wechatmoment.service.MomentsService
import com.android_practice.wechatmoment.service.ServiceBuilder

class MomentsViewModel() : ViewModel() {
    private val momentItemDao =
        WechatDatabase.getInstance(MainApplication.appContext()).momentItemDao()
    private val momentItemApi = ServiceBuilder.buildService(MomentsService::class.java)
    private val appExecutors = AppExecutors()
    val momentItems = MomentsRepo(momentItemApi, momentItemDao, appExecutors).getMomentItemInfo()
}