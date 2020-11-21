package com.example.exoplayerdemo.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.exoplayerdemo.R
import com.example.exoplayerdemo.databinding.ActivityVideoListBinding
import com.example.exoplayerdemo.model.databean.VideoData
import com.example.exoplayerdemo.model.databean.VideoDataList
import com.example.exoplayerdemo.utility.DemoMediaData

class ViewListActivity : AppCompatActivity() {
    private lateinit var viewDataBinding: ActivityVideoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_list)
        viewDataBinding.videoList = createVideoDataList()
    }

    private fun createVideoDataList() : VideoDataList = run {
        val videoDataList = VideoDataList()
        for(index in DemoMediaData.data.indices) {
            videoDataList.addVideoData(
                VideoData()
                    .setVideoUrl(DemoMediaData.data.get(index).first)
                    .setVideoName("")
                    .setThumbnailUrl(DemoMediaData.data.get(index).second)
            )
        }

        videoDataList
    }
}