package com.platform.api.repository

import com.platform.api.models.Grade
import com.platform.api.models.GradeYear
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface GradeRepository : MongoRepository<Grade, ObjectId>
{
}