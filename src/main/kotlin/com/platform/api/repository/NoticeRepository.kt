package com.platform.api.repository

import com.platform.api.models.BoardPost
import com.platform.api.models.NoticePost
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository


interface NoticeRepository : MongoRepository<NoticePost, String>
{
    fun findByPublished(published: Boolean): List<NoticePost>
    fun findByTitleContaining(title: String): List<NoticePost>
}