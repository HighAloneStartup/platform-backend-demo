package com.platform.api.payload.response

import com.platform.api.models.BoardPost
import com.platform.api.models.Comment
import com.platform.api.models.NoticePost
import com.platform.api.models.User
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.Instant
import java.util.*
import java.util.stream.Collectors

class NoticeListResponse (
        noticePost: NoticePost
)
{
    val id: String = noticePost.id
    val title: String = noticePost.title
    val description: String = if(noticePost.description.length > 17)
        noticePost.description.substring(0, 17) +  "..." else  noticePost.description
    val fileLink: String = noticePost.fileLink
    val published: Boolean = noticePost.published

    val createdAt: Instant = noticePost.createdAt
    var likes: Int = noticePost.likes.size
    var images: Int = noticePost.images.size
    var comments: Int = noticePost.comments.size
}