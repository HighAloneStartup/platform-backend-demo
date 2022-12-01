package com.platform.api.payload.request

import com.platform.api.models.Role
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import java.util.*

class ProfileRequest (
        var phoneNumber: String? = null,
        val photoUrl: String? = null
)