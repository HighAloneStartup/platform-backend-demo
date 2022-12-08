package com.platform.api.payload.request

import javax.validation.constraints.NotBlank

class CommentRequest (
        val description: @NotBlank String,
        val anonymous: Boolean = true,
)