package com.example.exoplayerdemo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.exoplayerdemo.R
import com.example.exoplayerdemo.databinding.ExoPlayerViewListItemBinding
import com.example.exoplayerdemo.model.databean.VideoData
import com.example.exoplayerdemo.model.databean.VideoDataList
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class VideoListAdapter(private val context: Context, private val videoDataList: VideoDataList): RecyclerView.Adapter<VideoListAdapter.Holder>() {
    private lateinit var viewDataBinding: ExoPlayerViewListItemBinding
    private lateinit var layoutInflater: LayoutInflater
    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var isManualPlayed: Boolean = false
    private var autoPlayIndex: Int = 0

    init {
        simpleExoPlayer = SimpleExoPlayer.Builder(context).build()
        simpleExoPlayer?.playWhenReady = true
    }
    
    fun setAutoPlayIndex(data: Int, forcePlay: Boolean = false) {
        if((autoPlayIndex != data && !isManualPlayed) || forcePlay) {
            val oldIndex = autoPlayIndex
            this.autoPlayIndex = data

            if(oldIndex != -1) notifyItemChanged(oldIndex)
            notifyItemChanged(autoPlayIndex)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = run {
        if(!::layoutInflater.isInitialized) layoutInflater = LayoutInflater.from(parent.context)
        viewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.exo_player_view_list_item, parent, false)
        Holder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if(holder.viewDataBinding is ExoPlayerViewListItemBinding) {
            if(videoDataList.get(holder.adapterPosition) != null) {
                val videoData: VideoData = videoDataList.get(holder.adapterPosition)!!

                holder.viewDataBinding.thumbnailUrl = if(holder.adapterPosition != autoPlayIndex) videoData.getThumbnailUrl() else null
                if(holder.adapterPosition == autoPlayIndex) prepareAndPlay(holder.viewDataBinding.playerView, videoData.getVideoUrl())

                holder.viewDataBinding.playMediaButton.setOnClickListener {
                    isManualPlayed = true
                    setAutoPlayIndex(holder.adapterPosition, true)
                }
            }
        }
    }

    private fun prepareAndPlay(playerView: PlayerView, mediaUrl: String?) {
        if(simpleExoPlayer != null && mediaUrl != null) {
            playerView.player = simpleExoPlayer
            simpleExoPlayer?.setMediaItem(MediaItem.fromUri(mediaUrl))
            simpleExoPlayer?.prepare()
            simpleExoPlayer?.play()
        }
    }

    override fun getItemCount() = videoDataList.getSize()

    open class Holder(val viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder(viewDataBinding.root)
}