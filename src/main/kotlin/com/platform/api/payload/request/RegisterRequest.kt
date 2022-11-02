package com.platform.api.payload.request

import javax.validation.constraints.*

class RegisterRequest(
        var name: @NotBlank @Size(min = 3, max = 20) String,
        var email: @NotBlank @Size(max = 50) @Email String,
        var password: @NotBlank @Size(min = 6, max = 40) String,
        var roles: Set<String>? = null,
        var gradeYear: @Size(min = 1, max = 1) @Min(1) @Max(3) Int?,
        var classGroup: @Min(1) Int?,
)