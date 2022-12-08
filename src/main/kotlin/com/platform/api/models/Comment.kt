package com.platform.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "comment")
class Comment(
    @Id
    val id: String = String(),
    @DBRef
    val user: User,
    val anonymous: Boolean = true,

    val description: String = String(),
    val createdAt: Instant = Instant.now()
)
{
    open fun getObjectId(): String
    {
        return id
    }
}
