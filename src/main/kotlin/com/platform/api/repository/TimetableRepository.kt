package com.platform.api.repository

import com.platform.api.models.Board
import com.platform.api.models.Timetable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
open interface TimetableRepository : MongoRepository<Timetable, String> {
    fun findByName(name: String): Timetable
}