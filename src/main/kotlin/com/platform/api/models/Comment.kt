package com.platform.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "comment")
class Comment
{
    @Id
    private val id: String? = null
    private val userId: String? = null
    private val userFullname: String? = null
    private val content: String? = null
    private val createdAt: Instant? = null
}
