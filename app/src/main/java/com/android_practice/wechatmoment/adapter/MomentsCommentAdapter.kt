package com.android_practice.wechatmoment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android_practice.wechatmoment.R
import com.android_practice.wechatmoment.model.CommentResponse
import kotlinx.android.synthetic.main.tweet_item_comment.view.*

class MomentsCommentAdapter(private val commentResponses: List<CommentResponse>) :
    RecyclerView.Adapter<MomentsCommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentsCommentViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tweet_item_comment, parent, false)
        return MomentsCommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentResponses.size
    }

    override fun onBindViewHolder(holder: MomentsCommentViewHolder, position: Int) {
        return holder.bind(commentResponses[position])
    }

}

class MomentsCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(commentResponse: CommentResponse) {
        itemView.comment_name_textView.text = commentResponse.sender.username
        itemView.comment_content_textView.text = commentResponse.content
    }
}