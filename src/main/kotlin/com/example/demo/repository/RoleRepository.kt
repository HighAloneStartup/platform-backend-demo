package com.example.demo.repository

import com.example.demo.models.ERole
import com.example.demo.models.Role
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface RoleRepository : MongoRepository<Role, String> {
    fun findByName(name: ERole): Optional<Role>
}