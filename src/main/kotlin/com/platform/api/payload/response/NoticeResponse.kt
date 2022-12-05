package com.platform.api.payload.response

import com.platform.api.models.BoardPost
import com.platform.api.models.Comment
import com.platform.api.models.NoticePost
import com.platform.api.models.User
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import java.time.Instant
import java.util.ArrayList

class NoticeResponse(
        noticePost: NoticePost,
        liked: Boolean = false,
)
{
    val id: String = noticePost.id
    val title: String = noticePost.title
    val description: String = if(noticePost.description.length > 20)
        noticePost.description.substring(0, 20) else  noticePost.description
    val published: Boolean = noticePost.published

    val fileLink: String = noticePost.fileLink
    val fileName: String = noticePost.fileName

    val createdAt: Instant = noticePost.createdAt

    val liked: Boolean = liked
    val likes: Int = noticePost.likes.size
    val images: ArrayList<String> = noticePost.images
    val comments: ArrayList<Comment> = noticePost.comments
}