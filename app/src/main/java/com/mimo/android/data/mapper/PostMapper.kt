package com.mimo.android.data.mapper

import com.mimo.android.data.model.PostListResponse
import com.mimo.android.domain.model.Post

fun PostListResponse.toPostList(): List<Post> {
    return this.data.map {
        Post(
            id = it.id,
            title = it.title,
            userId = it.userInfo.id,
            videoUrl = it.videoUrl,
            tagList = it.tagList.map { tag ->
                Post.Tag(
                    id = tag.id,
                    name = tag.name,
                )
            },
            profileImageUrl = it.userInfo.profileImageUrl,
            userName = it.userInfo.userName,
            videoThumbnailUrl = it.videoThumbnailUrl,
            uploadTime = it.uploadTime,
        )
    }
}
