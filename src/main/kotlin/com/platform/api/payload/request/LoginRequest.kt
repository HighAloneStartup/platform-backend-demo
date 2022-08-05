package com.platform.api.payload.request

import javax.validation.constraints.NotBlank

data class LoginRequest(
        var email: @NotBlank String,
        var password: @NotBlank String
)