package com.platform.api.repository

import com.platform.api.models.Role
import org.springframework.data.mongodb.repository.MongoRepository

interface ClassGroupRepository : MongoRepository<Role?, String?>
{
}