package com.android_practice.wechatmoment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class NineImageAdapter {

    abstract fun getItemCount(): Int

    abstract fun createView(
        inflater: LayoutInflater,
        parent: ViewGroup,
        position: Int
    ): View?

    abstract fun bindView(view: View, position: Int)

    open fun OnItemClick(position: Int, view: View) {}


}