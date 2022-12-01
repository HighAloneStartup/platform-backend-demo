package com.platform.api.payload.response

import com.platform.api.models.BoardPost
import com.platform.api.models.Comment
import com.platform.api.models.User
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import java.time.Instant
import java.util.ArrayList

class PostResponse(
        boardPost: BoardPost
)
{
        val id: String = boardPost.id
        val title: String = boardPost.title
        val description: String = if(boardPost.description.length > 20)
                boardPost.description.substring(0, 20) else  boardPost.description
        val published: Boolean = boardPost.published

        val uid: String = boardPost.user.uid
        val userName: String = boardPost.user.name
        val userPhotoUrl: String? = boardPost.user.photoUrl

        val anonymous: Boolean = boardPost.anonymous
        val createdAt: Instant = boardPost.createdAt

        var isLiked: Boolean = boardPost.likes.contains(uid)
        var images: Array<String> = boardPost.images
        var comments: Array<Comment> = boardPost.comments
}