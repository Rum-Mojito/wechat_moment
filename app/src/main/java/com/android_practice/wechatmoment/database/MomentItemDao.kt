package com.android_practice.wechatmoment.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android_practice.wechatmoment.model.Comment
import com.android_practice.wechatmoment.model.MomentItem
import com.android_practice.wechatmoment.model.MomentItemWithComment

@Dao
interface MomentItemDao {

    @Query("SELECT * FROM moment_item")
    fun getAllMomentItems(): LiveData<List<MomentItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMomentItems(vararg moment: MomentItem): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMomentItemWithComments(comments: List<Comment>, vararg moment: MomentItem)

    @Query("SELECT * FROM comment")
    fun getAllComments(): LiveData<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComments(vararg comment: Comment)

    @Transaction
    @Query("SELECT * FROM moment_item")
    fun getMomentItemWithComments(): List<MomentItemWithComment>

    @Query("DELETE FROM comment")
    fun deleteAllComments()

    @Query("DELETE FROM moment_item")
    fun deleteAllMoments()

}