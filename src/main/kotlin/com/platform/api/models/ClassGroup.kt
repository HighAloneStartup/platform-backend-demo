package com.platform.api.models

import org.bson.types.ObjectId

class ClassGroup(
        val number: Int,
        val gradeYear: Int,
        val division: EDivision,
        val userIds: List<ObjectId>
)
