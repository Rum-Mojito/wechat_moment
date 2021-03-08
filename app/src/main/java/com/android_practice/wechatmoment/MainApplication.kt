package com.android_practice.wechatmoment

import android.app.Application
import android.content.Context
import com.android_practice.wechatmoment.di.momentsModelModule
import com.android_practice.wechatmoment.di.userModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication

        fun appContext(): Context = instance.applicationContext

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(listOf(userModelModule, momentsModelModule))
        }
    }
}