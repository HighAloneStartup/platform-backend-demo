package com.platform.api.repository

import com.platform.api.models.ERole
import com.platform.api.models.Role
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*


interface RoleRepository : MongoRepository<Role?, String?>
{
    fun findByName(name: ERole?): Optional<Role?>?
}