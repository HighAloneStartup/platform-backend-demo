package com.platform.api.repository

import com.platform.api.models.ClassGroup
import com.platform.api.models.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UserRepository : MongoRepository<User?, ObjectId?>
{
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun findByGradeYear(gradeYear: Int): List<User?>?
    fun findByClassGroup(classGroup: Int): List<User?>?
    fun findByGradeYearAndClassGroup(gradeYear: Int, classGroup: Int): List<User?>?
}