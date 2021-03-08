package com.android_practice.wechatmoment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android_practice.wechatmoment.model.Comment
import com.android_practice.wechatmoment.model.MomentItem
import com.android_practice.wechatmoment.model.User


@Database(entities = [User::class, MomentItem::class, Comment::class], version = 1)
abstract class WechatDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun momentItemDao(): MomentItemDao

    companion object {
        private var INSTANCE: WechatDatabase? = null


        fun getInstance(context: Context): WechatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WechatDatabase::class.java,
                    "wechatmoment.db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun getDatabase(): WechatDatabase? {
            return INSTANCE
        }
    }
}
