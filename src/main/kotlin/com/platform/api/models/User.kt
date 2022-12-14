package com.platform.api.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "users")
class User(
        @Id
        val uid: String = String(),
        val name: String,
        @DBRef
        val roles: ArrayList<Role> = ArrayList<Role>(),

        val email: String,
        val encryptedPassword: String,

        val gradeYear: Int? = null,
        val classGroup: Int? = null,
        val attendanceNumber: Int? = null,

        val generationNumber: Int? = null,
        val studentNumber: Int? = null,
        val birthday: Date? = null,
        var phoneNumber: String? = null,

        var photoUrl: String? = null
)
{
        open fun getObjectId(): String
        {
                return uid
        }
}