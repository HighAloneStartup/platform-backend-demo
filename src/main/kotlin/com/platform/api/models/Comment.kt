package com.platform.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "comment")
class Comment(
    @Id
    val uId: String = String(),
    val userName: String = String(),
    val userImage: String? = null,
    val anonymous: Boolean = true,

    val decription: String = String(),
    val createdAt: Instant = Instant.now()
)
{

}
