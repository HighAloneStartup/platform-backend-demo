package com.platform.api.payload.request

import com.platform.api.models.Role
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import java.util.ArrayList

class ClassGroupRequest (
        val name: String = String(),
        val year: Int,
        val grade: Int,
        val classGroup: Int
)