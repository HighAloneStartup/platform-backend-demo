package com.platform.api.repository

import com.platform.api.models.Post
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*


interface PostRepository : MongoRepository<Post?, String?>
{
    fun findByPublished(published: Boolean): List<Post?>?
    fun findByTitleContaining(title: String?): List<Post?>?

    fun findByUserId(id: String?): List<Post?>?
    fun findByUserIdOrderByCreatedAtDesc(id: String?): List<Post?>?
}