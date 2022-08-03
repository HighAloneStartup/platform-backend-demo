package com.example.demo.payload.request

import javax.validation.constraints.NotBlank

data class LoginRequest(
        var username: @NotBlank String,
        var password: @NotBlank String
)