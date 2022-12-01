package com.platform.api.payload.request

import com.platform.api.models.Comment
import com.platform.api.models.Role
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import java.time.Instant
import java.util.ArrayList
import java.util.HashSet
import javax.validation.constraints.*

class PostRequest(
        var title: @NotBlank String,
        var description: @NotBlank String,
        var published: Boolean = true,
        val anonymous: Boolean = true,
        val images: Array<String>? = arrayOf<String>()
)