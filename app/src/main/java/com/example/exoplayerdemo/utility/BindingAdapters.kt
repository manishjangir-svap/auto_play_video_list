package com.example.exoplayerdemo.utility

import android.app.Activity
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.example.exoplayerdemo.model.databean.VideoDataList
import com.example.exoplayerdemo.view.adapter.VideoListAdapter
import java.util.*

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("app:addAdapterData")
    fun RecyclerView.appVideoListAdapter(videoDataList: VideoDataList?) {
        if(videoDataList != null) {
            val adapter = VideoListAdapter(this.context, videoDataList)
            val linearLayoutManager = LinearLayoutManager(this.context)
            this.layoutManager = linearLayoutManager
            this.adapter = adapter
            this.addItemDecoration(RecyclerViewDecorationTopPadding(this.context, 16))
            (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                private lateinit var timerTask: TimerTask

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {
                            timerTask = object : TimerTask() {
                                override fun run() {
                                    (this@appVideoListAdapter.context as Activity).runOnUiThread {
                                        val visibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                                        adapter.setAutoPlayIndex(visibleItem)
                                    }
                                }
                            }

                            Timer().schedule(timerTask, 300)
                        }
                        else -> {
                            if(::timerTask.isInitialized) timerTask.cancel()
                        }
                    }
                }
            })
        }
    }

    @JvmStatic
    @BindingAdapter("app:thumbnailUrl")
    fun ImageView.addThumbnail(thumbnailUrl: String?) {
        if(thumbnailUrl != null && thumbnailUrl.isNotEmpty()) Glide.with(this.context).load(thumbnailUrl).into(this)
    }
}