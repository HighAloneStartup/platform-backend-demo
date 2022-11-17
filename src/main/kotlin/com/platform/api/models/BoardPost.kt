package com.platform.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "posts")
class BoardPost(
        @Id
        val id: String = String(),
        val title: String? = null,
        val description: String? = null,
        val published: Boolean = true
)
{
    override fun toString(): String
    {
        return "Post [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]"
    }
}