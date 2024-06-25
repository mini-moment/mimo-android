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
    val markerId: Int,
    val tagList: List<Tag>,
    val profileImageUrl: String,
    val userName: String,
    val videoThumbnailUrl: String
) : Parcelable {

    @Parcelize
    data class Tag(
        val id: Int,
        val name: String
    ) : Parcelable

    companion object{
        val DEFAULT = PostData(-1,"", -1, "", -1, emptyList(), "", "", "")
    }
}

fun PostListResponse.toPostData(): List<PostData> {
    return this.data.map {
        PostData(
            id = it.id,
            title = it.title,
            userId = it.userId,
            videoUrl = it.videoUrl,
            markerId = it.markerId,
            tagList = it.tagList.map { tag ->
                PostData.Tag(
                    id = tag.id,
                    name = tag.name
                )
            },
            profileImageUrl = it.userInfo.profileImageUrl,
            userName = it.userInfo.userName,
            videoThumbnailUrl = it.videoThumbnailUrl
        )
    }
}

fun List<PostData>.findPostIndex(postId : Int) : Int{
    return this.indexOf(this.filter { it.userId == postId }[0])
}