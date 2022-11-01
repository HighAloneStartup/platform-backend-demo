package com.platform.api.payload.request

import com.platform.api.models.GradeYear
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class RegisterRequest(
        var name: @NotBlank @Size(min = 3, max = 20) String,
        var email: @NotBlank @Size(max = 50) @Email String,
        var password: @NotBlank @Size(min = 6, max = 40) String,
        var roles: Set<String>? = null,
        var gradeYear: @Size(min = 1, max = 1) GradeYear?,
        var classGroup: Int?,
)