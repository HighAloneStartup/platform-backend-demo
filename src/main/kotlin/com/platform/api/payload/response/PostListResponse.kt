package com.platform.api.payload.response

import com.platform.api.models.BoardPost
import com.platform.api.models.Comment
import com.platform.api.models.User
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.Instant
import java.util.*
import java.util.stream.Collectors

class PostListResponse (
    boardPost: BoardPost
)
{
    val id: String = boardPost.id
    val title: String = boardPost.title
    val description: String = if(boardPost.description.length > 17)
        boardPost.description.substring(0, 17) +  "..." else  boardPost.description
    val published: Boolean = boardPost.published

    val uid: String = boardPost.user.uid
    val userName: String = boardPost.user.name
    val userPhotoUrl: String? = boardPost.user.photoUrl

    val anonymous: Boolean = boardPost.anonymous

    val createdAt: Instant = boardPost.createdAt
    var likes: Int = boardPost.likes.size
    var images: Int = boardPost.images.size
    var comments: Int = boardPost.comments.size
}