package com.platform.api.payload.request

import com.platform.api.models.Role
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import java.util.*

class ProfileRequest (
        val gradeYear: Int? = null,
        val classGroup: Int? = null,
        var phoneNumber: String? = null,
        val photoUrl: String? = null,
        val roles: ArrayList<String>? = ArrayList<String>()
)