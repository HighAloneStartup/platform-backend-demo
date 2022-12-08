package com.platform.api.payload.response

import com.platform.api.models.BoardPost
import java.time.Instant

class ClassGroupPostListResponse(
        boardPost: BoardPost
)
{
    val id: String = boardPost.id
    val published: Boolean = boardPost.published
    val createdAt: Instant = boardPost.createdAt
    val image: String = if(boardPost.images.size > 0) boardPost.images[0] else null.toString()
    val manyimages: Boolean = boardPost.images.size > 0
}