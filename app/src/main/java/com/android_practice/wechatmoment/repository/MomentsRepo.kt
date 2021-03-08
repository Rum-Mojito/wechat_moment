package com.android_practice.wechatmoment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android_practice.wechatmoment.database.MomentItemDao
import com.android_practice.wechatmoment.model.*
import com.android_practice.wechatmoment.service.ApiSuccessResponse
import com.android_practice.wechatmoment.service.AppExecutors
import com.android_practice.wechatmoment.service.MomentsService

class MomentsRepo(
    private val momentsApi: MomentsService,
    private val momentItemDao: MomentItemDao,
    private val appExecutors: AppExecutors
) {

    fun getMomentItemInfo(): LiveData<Resource<List<MomentItemResponse>>> {
        return object :
            NetworkBoundResource<List<MomentItemResponse>, List<MomentItemResponse>>(appExecutors) {
            override fun saveCallResult(item: List<MomentItemResponse>) {

                momentItemDao.deleteAllComments()
                momentItemDao.deleteAllMoments()

                item.forEach {
                    val momentItem = MomentItem(null, it.content, it.images, it.sender)

                    val insertedMomentItemId = momentItemDao.insertMomentItems(momentItem)[0]
                    it.commentResponses?.forEach { commentResponse ->
                        momentItemDao.insertComments(
                            Comment(
                                null,
                                insertedMomentItemId,
                                commentResponse.content,
                                commentResponse.sender
                            )
                        )
                    }

                }

            }

            //TODO: 把判断是否抓取数据的逻辑放在这里
            override fun shouldFetch(data: List<MomentItemResponse>?) =
                (data as ArrayList<MomentItemResponse>).size == 0

            override fun loadFromDb(): LiveData<List<MomentItemResponse>> {
                val momentWithCommentsFromDB = momentItemDao.getMomentItemWithComments()
                val momentItemResponses = ArrayList<MomentItemResponse>()
                momentWithCommentsFromDB.forEach {
                    momentItemResponses.add(momentWithCommentsToMomentResponse(it))
                }
                val momentItemResponseLiveData: MutableLiveData<List<MomentItemResponse>> =
                    MutableLiveData()
                momentItemResponseLiveData.postValue(momentItemResponses)

                return momentItemResponseLiveData
            }

            override fun createCall() = momentsApi.getMomentsInfo()

            override fun processResponse(response: ApiSuccessResponse<List<MomentItemResponse>>) =
                response.body.filterNot { momentFilterNot(it) }

        }.asLiveData()

    }

    fun momentWithCommentsToMomentResponse(momentWithComments: MomentItemWithComment): MomentItemResponse {

        val commentResponse = ArrayList<CommentResponse>()
        momentWithComments.comments.forEach {
            commentResponse.add(commentToCommentResponse(it))
        }

        val momentItem = momentWithComments.momentItem

        return MomentItemResponse(
            commentResponse,
            momentItem.content,
            momentItem.images,
            momentItem.sender
        )
    }

    private fun commentToCommentResponse(comment: Comment) =
        CommentResponse(comment.content, comment.sender)

    private fun momentFilterNot(momentItemResponse: MomentItemResponse): Boolean {
        return momentItemResponse.sender == null || (momentItemResponse.content.isNullOrEmpty() && momentItemResponse.images == null)
    }

}
