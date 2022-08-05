package com.platform.api.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "users")
class User(
        @Id
        val uid: ObjectId = ObjectId(),
        val name: String,
        val role: UserRole,

        val email: String,
        val encryptedPassword: String,

        val gradeYear: GradeYear? = null,
        val classGroup: Int? = null,
        val attendanceNumber: Int? = null,

        val generationNumber: Int? = null,
        val studentNumber: Int? = null,
        val birthday: Date? = null,
        val phoneNumber: String? = null,

        val photoUrl: String? = null
)

enum class UserRole {
    ROLE_ADMIN,
    ROLE_TEACHER,
    ROLE_STUDENT,
}