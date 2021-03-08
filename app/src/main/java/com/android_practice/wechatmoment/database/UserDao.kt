package com.android_practice.wechatmoment.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.android_practice.wechatmoment.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    fun getAllUsers(): LiveData<User>

    @Query("SELECT * FROM user WHERE username = :userName")
    fun getUserByName(userName: String): LiveData<User>

    @Insert(onConflict = REPLACE)
    fun insertUsers(vararg users: User)

    @Query("DELETE FROM user")
    fun deleteAll()

    @Delete
    fun deleteUsers(vararg user: User)

    @Update
    fun updateUsers(vararg users: User)

}
