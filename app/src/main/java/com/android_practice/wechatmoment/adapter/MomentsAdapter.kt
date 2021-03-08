package com.android_practice.wechatmoment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android_practice.wechatmoment.R
import com.android_practice.wechatmoment.model.MomentItemResponse
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.footer.view.*
import kotlinx.android.synthetic.main.tweet_item.view.*

class MomentsAdapter(
    private var momentItemResponseList: List<MomentItemResponse>,
    private var totalMomentItemResponseList: List<MomentItemResponse>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TWEET_ITEM_TYPE = 0
        const val FOOTER_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            FOOTER_TYPE -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.footer, parent, false)
                FooterViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.tweet_item, parent, false)
                MomentsViewHolder(view)
            }
        }
    }

    fun setMoments(newMomentItemResponseList: List<MomentItemResponse>) {
        momentItemResponseList = newMomentItemResponseList
    }

    override fun getItemCount(): Int {
        return momentItemResponseList.size + 1
    }

    fun getRealItemCount(): Int {
        return momentItemResponseList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FooterViewHolder) {
            val sizeGap = totalMomentItemResponseList.size - momentItemResponseList.size
            if (sizeGap >= 5) {
                return holder.bind("loding more")
            } else if (sizeGap > 0) {
                return holder.bind("loding more")
            } else {
                return holder.bind("no more moments")
            }

        } else if (holder is MomentsViewHolder) {
            return holder.bind(momentItemResponseList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount - 1 -> FOOTER_TYPE
            else -> TWEET_ITEM_TYPE
        }
    }

}

class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(loadingMessage: String) {
        itemView.tips.text = loadingMessage
    }
}

class MomentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(momentItemResponse: MomentItemResponse) {
        Glide.with(itemView.context).load(momentItemResponse.sender?.avatar)
            .into(itemView.moment_imageView_avatar)
        itemView.username_text_view.text = momentItemResponse.sender?.username

        itemView.content_text_view.text = momentItemResponse.content

        itemView.nine_image_layout.visibility = when {
            momentItemResponse.images != null -> {
                itemView.nine_image_layout.apply {
                    setAdapter(MomentsImageAdapter(momentItemResponse.images, this))
                }
                View.VISIBLE
            }
            else -> {
                View.GONE
            }
        }

        itemView.comments_recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = momentItemResponse.commentResponses?.let { MomentsCommentAdapter(it) }
        }
    }
}
