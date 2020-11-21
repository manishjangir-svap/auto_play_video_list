package com.example.exoplayerdemo.model.databean

class VideoDataList {
    private lateinit var items: ArrayList<VideoData>

    fun setVideoData(data: ArrayList<VideoData>) { this.items = data }
    fun addVideoData(data: VideoData) {
        if(!::items.isInitialized) items = ArrayList()
        items.add(data)
    }

    fun getSize(): Int = run { if(!::items.isInitialized) 0 else items.size }
    fun get(index: Int): VideoData? = run { if(!::items.isInitialized) null else items[index] }
}

class VideoData {
    private var thumbnailUrl: String? = null
    private var videoUrl: String? = null
    private var videoName: String? = null

    fun getThumbnailUrl(): String? = thumbnailUrl
    fun getVideoUrl(): String? = videoUrl
    fun getVideoName(): String? = videoName

    fun setThumbnailUrl(data: String): VideoData = run { this.thumbnailUrl = data; this }
    fun setVideoUrl(data: String): VideoData = run { this.videoUrl = data; this }
    fun setVideoName(data: String): VideoData = run { this.videoName = data; this }
}