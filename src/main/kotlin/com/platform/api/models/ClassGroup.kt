package com.platform.api.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.ArrayList


@Document(collection = "classgroups")
class ClassGroup (
        @Id
        val id: String = String(),
        val name: String = String(),
        val gradeYear: Int,
        val classGroup: Int,
        @DBRef
        val roles: ArrayList<Role> = ArrayList<Role>(),
        val photoUrl: String = String()
)
{
        open fun getObjectId(): String
        {
                return id
        }
}

