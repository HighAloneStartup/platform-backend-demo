package com.platform.api.repository

import com.platform.api.models.Board
import com.platform.api.models.ClassGroup
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ClassGroupRepository : MongoRepository<ClassGroup, String> {
    fun findByName(name: String): Board
}