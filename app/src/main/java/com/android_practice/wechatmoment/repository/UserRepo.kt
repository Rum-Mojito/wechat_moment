package com.android_practice.wechatmoment.repository

import androidx.lifecycle.LiveData
import com.android_practice.wechatmoment.database.UserDao
import com.android_practice.wechatmoment.model.Resource
import com.android_practice.wechatmoment.model.User
import com.android_practice.wechatmoment.service.AppExecutors
import com.android_practice.wechatmoment.service.UserService

class UserRepo(
    private val userApi: UserService,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors
) {

    fun getUserInfo(): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun saveCallResult(item: User) {
                userDao.insertUsers(item)
            }

            override fun shouldFetch(data: User?) = data == null

            override fun loadFromDb(): LiveData<User> = userDao.getAllUsers()

            override fun createCall() = userApi.getUserInfo()

        }.asLiveData()
    }

}


