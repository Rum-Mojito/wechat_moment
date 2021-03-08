package com.android_practice.wechatmoment.viewmodel

import androidx.lifecycle.ViewModel
import com.android_practice.wechatmoment.MainApplication
import com.android_practice.wechatmoment.database.WechatDatabase
import com.android_practice.wechatmoment.repository.UserRepo
import com.android_practice.wechatmoment.service.AppExecutors
import com.android_practice.wechatmoment.service.ServiceBuilder
import com.android_practice.wechatmoment.service.UserService

class MainViewModel : ViewModel() {

    private val userDao = WechatDatabase.getInstance(MainApplication.appContext()).userDao()
    private val userApi = ServiceBuilder.buildService(UserService::class.java)
    private val appExecutors = AppExecutors()
    val userInfo = UserRepo(userApi, userDao, appExecutors).getUserInfo()


}
