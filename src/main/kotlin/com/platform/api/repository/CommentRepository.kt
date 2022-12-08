package com.platform.api.repository

import com.platform.api.models.Board
import com.platform.api.models.ClassGroup
import com.platform.api.models.Comment
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : MongoRepository<Comment, String> {
    fun findByDescription(description: String): Comment
}