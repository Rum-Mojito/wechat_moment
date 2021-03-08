package com.android_practice.wechatmoment.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android_practice.wechatmoment.R
import com.android_practice.wechatmoment.adapter.NineImageAdapter


class NineImageLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ViewGroup(context, attrs, defStyleAttr) {
    /**
     * 控件宽度
     */
    private var layoutWidth = 300

    /**
     * 图片之间间隔的大小
     */
    private var itemMargin = 5

    /**
     * 单个图片的宽度和高度
     */
    private val itemWidth: Int

    /**
     * 一张图片允许的最大宽高范围
     */
    private var singleImageWidth = 200

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.NineImageLayout)
        singleImageWidth = array.getDimensionPixelSize(
            R.styleable.NineImageLayout_nine_singleImageWidth,
            dip2px(getContext(), singleImageWidth.toFloat())
        )
        itemMargin = array.getDimensionPixelSize(
            R.styleable.NineImageLayout_nine_imageGap,
            dip2px(getContext(), itemMargin.toFloat())
        )
        layoutWidth = array.getDimensionPixelSize(
            R.styleable.NineImageLayout_nine_layoutWidth,
            dip2px(getContext(), layoutWidth.toFloat())
        )
        array.recycle()

        itemWidth = (layoutWidth - 2 * itemMargin) / 3
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //一张图片的宽高
        var viewHeight = 0
        var viewWidth = 0
        val count = childCount
        if (count == 1) {
            //TODO 单独处理
            setMeasuredDimension(singleViewWidth, singleViewHeight)
            return
        } else if (count <= 3) {
            viewHeight = itemWidth
            if (count == 2) {
                viewWidth = 2 * itemWidth + itemMargin
            } else if (count == 3) {
                viewWidth = layoutWidth
            }
        } else if (count <= 6) {
            viewHeight = 2 * itemWidth + itemMargin
            viewWidth = if (count == 4) {
                2 * itemWidth + itemMargin
            } else {
                layoutWidth
            }
        } else if (count <= 9) {
            viewHeight = layoutWidth
            viewWidth = layoutWidth
        }
        setMeasuredDimension(viewWidth, viewHeight)
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        val count = childCount
        var top = 0
        var left = 0
        var right = 0
        var bottom = 0
        for (i in 0 until count) {
            val childView = getChildAt(i)
            when (i) {
                0 -> if (count == 1) {
                    //TODO 单独处理
                    left = 0
                    top = 0
                    right = left + singleViewWidth
                    bottom = top + singleViewHeight
                } else {
                    left = 0
                    top = 0
                    right = left + itemWidth
                    bottom = top + itemWidth
                }
                1 -> {
                    left = itemWidth + itemMargin
                    top = 0
                    right = left + itemWidth
                    bottom = top + itemWidth
                }
                2 -> if (count == 4) {
                    left = 0
                    top = itemWidth + itemMargin
                    right = left + itemWidth
                    bottom = top + itemWidth
                } else {
                    left = itemWidth * 2 + itemMargin * 2
                    top = 0
                    right = layoutWidth
                    bottom = top + itemWidth
                }
                3 -> if (count == 4) {
                    left = itemWidth + itemMargin
                    top = itemWidth + itemMargin
                    right = left + itemWidth
                    bottom = top + itemWidth
                } else {
                    left = 0
                    top = itemWidth + itemMargin
                    right = left + itemWidth
                    bottom = top + itemWidth
                }
                4 -> {
                    left = itemWidth + itemMargin
                    top = itemWidth + itemMargin
                    right = left + itemWidth
                    bottom = top + itemWidth
                }
                5 -> {
                    left = (itemWidth + itemMargin) * 2
                    top = itemWidth + itemMargin
                    right = layoutWidth
                    bottom = top + itemWidth
                }
                6 -> {
                    left = 0
                    top = (itemWidth + itemMargin) * 2
                    right = left + itemWidth
                    bottom = layoutWidth
                }
                7 -> {
                    left = itemWidth + itemMargin
                    top = (itemWidth + itemMargin) * 2
                    right = left + itemWidth
                    bottom = layoutWidth
                }
                8 -> {
                    left = (itemWidth + itemMargin) * 2
                    top = (itemWidth + itemMargin) * 2
                    right = layoutWidth
                    bottom = layoutWidth
                }
                else -> {
                }
            }
            childView.layout(left, top, right, bottom)
        }
    }

    var singleViewWidth = 0
    var singleViewHeight = 0

    /**
     * 传入单张图片的宽高
     *
     * @param width
     * @param height
     */
    @SuppressLint("LogNotTimber")
    fun setSingleImage(width: Int, height: Int, view: View?) {
        Log.i("test", "-------------------<<<<<<<<<childCount:$childCount")
        if (childCount != 1) {
            removeAllViews()
            addView(view)
        }
        Log.i(
            "test",
            "------------------->>>>>>>>>>width：$width    height:$height"
        )
        if (width >= height) {
            singleViewWidth = singleImageWidth
            singleViewHeight =
                (singleImageWidth * (height.toDouble() / width.toDouble())).toInt()
        } else {
            singleViewHeight = singleImageWidth
            singleViewWidth =
                (singleImageWidth * (width.toDouble() / height.toDouble())).toInt()
        }
        getChildAt(0).layout(0, 0, singleViewWidth, singleViewHeight)
        setMeasuredDimension(singleViewWidth, singleViewHeight)
    }

    /**
     * 设置数据源
     *
     * @param adapter
     */
    fun setAdapter(adapter: NineImageAdapter) {
        removeAllViews()
        for (i in 0 until adapter.getItemCount()) {
            val view: View? =
                adapter.createView(LayoutInflater.from(context), this, i)
            view?.let { adapter.bindView(it, i) }
            removeView(view)
            addView(view)
            view?.let { bindClickEvent(i, view, adapter) }
        }
    }

    /**
     * 绑定点击事件
     *
     * @param position
     * @param view
     */
    private fun bindClickEvent(
        position: Int,
        view: View,
        adapter: NineImageAdapter?
    ) {
        if (adapter == null) {
            return
        }
        view.setOnClickListener { adapter.OnItemClick(position, view) }
    }

    /**
     * 获取MarginLayoutParams必须要重写这几个方法
     *
     * @return
     */
    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


}