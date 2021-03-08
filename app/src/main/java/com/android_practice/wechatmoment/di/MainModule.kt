package com.android_practice.wechatmoment.di


import com.android_practice.wechatmoment.viewmodel.MainViewModel
import com.android_practice.wechatmoment.viewmodel.MomentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val userModelModule = module {
    viewModel { MainViewModel() }
}

val momentsModelModule = module {
    viewModel { MomentsViewModel() }
}

