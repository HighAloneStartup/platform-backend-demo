package com.platform.api.repository

import com.platform.api.models.BoardPost
import org.springframework.data.mongodb.repository.MongoRepository


interface BoardRepository : MongoRepository<BoardPost, String>
{
    fun findByPublished(published: Boolean): List<BoardPost>
    fun findByTitleContaining(title: String): List<BoardPost>
}