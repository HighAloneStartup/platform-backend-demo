package com.platform.api.models

import org.bson.types.ObjectId

class ClassGroup(
        val number: Int,
        val gradeYear: Int,
        val division: Division,
        val userIds: List<ObjectId>
)

enum class Division {
    SCIENCE,
    LITERATURE
}