package com.platform.api.repository

import com.platform.api.models.BoardPost
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
open interface PostRepository : MongoRepository<BoardPost, String>, PostRepositoryCustom
{
    open fun findByPublished(published: Boolean): List<BoardPost>
    open fun findByTitleContaining(title: String): List<BoardPost>
}