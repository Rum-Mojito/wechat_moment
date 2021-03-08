package com.android_practice.wechatmoment.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android_practice.wechatmoment.R
import com.android_practice.wechatmoment.model.Image
import com.android_practice.wechatmoment.ui.ImageRepo
import com.android_practice.wechatmoment.ui.NineImageLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.tweet_item_image.view.*


class MomentsImageAdapter(
    private val images: List<Image>,
    private val nineImageLayout: NineImageLayout
) : NineImageAdapter() {


    override fun getItemCount(): Int {
        return images.size
    }

    override fun createView(inflater: LayoutInflater, parent: ViewGroup, position: Int): View? {
        return inflater.inflate(R.layout.tweet_item_image, parent, false)
    }

    override fun bindView(view: View, position: Int) {


        val fileTemp by lazy {
            val filePath = view.context.cacheDir
            if(!filePath.exists()){
                filePath.mkdirs()
            }
            filePath
        }
        val imageRepo = ImageRepo(fileTemp)


        val context = nineImageLayout.context
        imageRepo.getImage(view.tweet_image_view, images[position], 0, 0)
//        Glide.with(context).load(images[position].url)
//            .into(
//                view.tweet_image_view
//            )

        if (images.size == 1) {
//            Glide.with(context)
//                .asBitmap()
//                .load(images[0].url)
//                .into(object : SimpleTarget<Bitmap?>() {
//                    override fun onResourceReady(
//                        bitmap: Bitmap,
//                        transition: Transition<in Bitmap?>?
//                    ) {
//                        val width = bitmap.width
//                        val height = bitmap.height
//                        nineImageLayout.setSingleImage(width, height, view.tweet_image_view)
//                    }
//                })
//            Glide.with(context).load(images[0].url)
//                .into(
//                    view.tweet_image_view
//                )
            nineImageLayout.setSingleImage(400, 400, view)
            imageRepo.getImage(view.tweet_image_view, images[0], 0, 0)
            imageRepo.getImage(view.tweet_image_view, images[0], 0, 0)
        } else {
//            Glide.with(context).load(images[position].url)
//                .into(
//                    view.tweet_image_view
//                )
            imageRepo.getImage(view.tweet_image_view, images[position], 0, 0)
        }
    }

    override fun OnItemClick(position: Int, view: View) {
        super.OnItemClick(position, view)
        // Toast.makeText(view.context, "position:" + images.get(position), Toast.LENGTH_SHORT).show()
    }

}