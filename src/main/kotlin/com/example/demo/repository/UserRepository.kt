package com.example.demo.repository

import com.example.demo.models.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UserRepository : MongoRepository<User?, String?> {
    fun findByUsername(username: String?): Optional<User?>
    fun existsByUsername(username: String?): Boolean
    fun existsByEmail(email: String?): Boolean
}