package com.platform.api.repository

import com.platform.api.models.Tutorial
import org.springframework.data.mongodb.repository.MongoRepository


interface TutorialRepository : MongoRepository<Tutorial?, String?> {
    fun findByPublished(published: Boolean): List<Tutorial?>?
    fun findByTitleContaining(title: String?): List<Tutorial?>?
}