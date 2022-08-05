package com.platform.api.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "grades")
@TypeAlias("Grade")
class Grade(
        @Id
        val id: ObjectId = ObjectId(),
        val gradeYear: GradeYear,
        val classGroups: List<ClassGroup> = listOf()
)

enum class GradeYear {
    FIRST,
    SECOND,
    THIRD
}