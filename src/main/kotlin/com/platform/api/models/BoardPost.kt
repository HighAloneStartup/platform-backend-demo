package com.platform.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*


@Document(collection = "#{@postRepository.getCollectionName()}")
open class BoardPost(
        @Id
        val id: String = String(),
        val title: String = String(),
        val description: String = String(),
        val published: Boolean = true,

        @DBRef
        val user: User,

        val anonymous: Boolean = true,

        val createdAt: Instant = Instant.now(),

        var likes: ArrayList<String> = ArrayList<String>(),
        var images: ArrayList<String> = ArrayList<String>(),
        @DBRef
        var comments: ArrayList<Comment> = ArrayList<Comment>()
)
{
    override fun toString(): String
    {
        return "Post [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]"
    }

    open fun getObjectId(): String
    {
        return id
    }
}