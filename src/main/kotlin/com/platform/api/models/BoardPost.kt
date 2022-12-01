package com.platform.api.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.ArrayList
import java.util.HashSet


@Document(collection = "posts")
class BoardPost(
        @Id
        val id: String = String(),
        val title: String = String(),
        val description: String = String(),
        val published: Boolean = true,

        @DBRef
        val user: User,

        val anonymous: Boolean = true,

        val createdAt: Instant = Instant.now(),
        @DBRef
        var likes: Array<String> = arrayOf<String>(),
        var images: Array<String> = arrayOf<String>(),
        @DBRef
        var comments: Array<Comment> = arrayOf<Comment>()
)
{
    override fun toString(): String
    {
        return "Post [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]"
    }
}