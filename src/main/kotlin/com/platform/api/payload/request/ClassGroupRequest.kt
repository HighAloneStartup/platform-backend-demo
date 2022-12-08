package com.platform.api.payload.request

import com.platform.api.models.Role
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import java.util.ArrayList

class ClassGroupRequest (
        val name: String = String(),
        val gradeYear: Int,
        val classGroup: Int,
        val roles: ArrayList<String>
)