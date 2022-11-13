package com.platform.api.payload.request

import org.springframework.security.core.GrantedAuthority
import java.util.*
import javax.validation.constraints.NotBlank

class ProfileRequest(
        var phoneNumber: String? = null,
        val photoUrl: String? = null
)