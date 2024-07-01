package com.mimo.android.domain.model

import android.os.Parcelable
import com.mimo.android.data.model.response.PostListResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostData(
    val id: Int,
    val title: String,
    val userId: Int,
    val videoUrl: String,
    val tagList: List<Tag>,
    val profileImageUrl: String,
    val userName: String,
    val videoThumbnailUrl: String,
    val uploadTime: String,
) : Parcelable {

    @Parcelize
    data class Tag(
        val id: Int,
        val name: String,
    ) : Parcelable
}

fun PostListResponse.toPostData(): List<PostData> {
    return this.data.map {
        PostData(
            id = it.id,
            title = it.title,
            userId = it.userInfo.id,
            videoUrl = it.videoUrl,
            tagList = it.tagList.map { tag ->
                PostData.Tag(
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

fun PostData.toTagData(): List<TagData> {
    return this.tagList.map {
        TagData(
            id = it.id,
            name = it.name
        )
    }
}

fun List<PostData>.findPostIndex(postId: Int): Int {
    return this.indexOf(this.filter { it.id == postId }[0])
}
