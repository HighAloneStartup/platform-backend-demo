package com.platform.api.repository

import com.platform.api.models.Board
import com.platform.api.models.BoardPost
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : MongoRepository<Board, String> {
    fun findByName(name: String): Board
}