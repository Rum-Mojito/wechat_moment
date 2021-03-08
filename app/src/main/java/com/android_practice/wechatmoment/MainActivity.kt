package com.android_practice.wechatmoment

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android_practice.wechatmoment.adapter.MomentsAdapter
import com.android_practice.wechatmoment.model.MomentItemResponse
import com.android_practice.wechatmoment.model.User
import com.android_practice.wechatmoment.viewmodel.MainViewModel
import com.android_practice.wechatmoment.viewmodel.MomentsViewModel
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val mainActivityViewModel by viewModel<MainViewModel>()
    private val momentsViewModel by viewModel<MomentsViewModel>()
    private var totalMoments = listOf<MomentItemResponse>()
    private var isLoading = false
    private lateinit var momentsAdapter: MomentsAdapter
    private lateinit var momentsLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(this.toolbar)
        mainActivityViewModel.userInfo.observe(this, Observer { user ->
            user?.data?.let { updateUser(it) }
        })
        momentsViewModel.momentItems.observe(
            this,
            Observer { moments -> moments?.data?.let { initMoments(it) } })
        initScrollListener()
        initAppBar()
    }

    private fun initAppBar() {

        app_bar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = 0
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == 0) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = false;
                    fab.visibility = View.GONE
                    username.visibility = View.GONE
                    friends_image_view.visibility = View.GONE
                    findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title =
                        "weChatMoment"

                } else if (!isShow) {
                    isShow = true;
                    fab.visibility = View.VISIBLE
                    username.visibility = View.VISIBLE
                    friends_image_view.visibility = View.VISIBLE
                    findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = " "
                }
            }
        })

    }

    private fun updateUser(user: User) {
        username.text = user.username
        Glide.with(this).load(user.profileImage).into(this.friends_image_view)
        Glide.with(this).load(user.avatar).into(this.fab)
    }

    private fun initSwipe() {
        swipe_container.setOnRefreshListener {
            updateMoments(totalMoments)
            swipe_container.isRefreshing = false
        }
        swipe_container.isRefreshing = false
    }

    private fun initMoments(momentItemResponseList: List<MomentItemResponse>) {

        this.totalMoments = momentItemResponseList
        this.momentsLayoutManager = LinearLayoutManager(this)
        var initMoments: List<MomentItemResponse> = ArrayList<MomentItemResponse>()
        if (momentItemResponseList.size >= 5) {
            initMoments = momentItemResponseList.subList(0, 5)
        }

        this.momentsAdapter =
            MomentsAdapter(initMoments, this@MainActivity.totalMoments)
        tweet_moments.apply {
            setHasFixedSize(true)
            this.layoutManager = this@MainActivity.momentsLayoutManager
            this.adapter = this@MainActivity.momentsAdapter
        }
        initSwipe()
    }

    private fun updateMoments(momentItemResponseList: List<MomentItemResponse>) {
        this.momentsAdapter.setMoments(momentItemResponseList.subList(0, 5))
        this.momentsAdapter.notifyDataSetChanged()
    }

    private fun initScrollListener() {
        tweet_moments.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!this@MainActivity.isLoading) {
                    if (this@MainActivity.momentsLayoutManager.findLastVisibleItemPosition() == this@MainActivity.momentsAdapter.getRealItemCount()) {
                        val sizeGap = totalMoments.size - momentsAdapter.getRealItemCount()
                        val loadMoreNo = if (sizeGap >= 5) {
                            5
                        } else if (sizeGap >= 0) {
                            sizeGap
                        } else {
                            0
                        }
                        loadMore(loadMoreNo)
                        this@MainActivity.isLoading = true
                    }
                }
            }

        })
    }

    private fun loadMore(loadMoreNo: Int) {
        Handler().postDelayed(
            {
                momentsAdapter.setMoments(
                    totalMoments.subList(
                        0,
                        momentsAdapter.getRealItemCount() + loadMoreNo
                    )
                )
                momentsAdapter.notifyDataSetChanged()
                isLoading = false;
            }, 1000
        )
    }
}
