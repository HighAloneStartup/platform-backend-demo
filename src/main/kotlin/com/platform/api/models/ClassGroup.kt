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
        val year: Int,
        val grade: Int,
        val classGroup: Int,
        val uids: ArrayList<String> = ArrayList<String>(),
        val photoUrl: String = String()
)
