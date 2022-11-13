package com.platform.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*


@Document(collection = "posts")
class Post
{
    @Id
    val id: String? = null
    var title: String? = null
    var description: String? = null
    var isPublished = false
    private val userId: String? = null
    private val originalUserId: String? = null
    private val content: String? = null
    private val image: String? = null
    private val createdAt: Instant? = null

    var love: List<String> = ArrayList()
    var share: List<String> = ArrayList()
    var comment: List<Comment> = ArrayList<Comment>()

    constructor()
    {
    }

    constructor(title: String?, description: String?, published: Boolean)
    {
        this.title = title
        this.description = description
        isPublished = published
    }

    override fun toString(): String
    {
        return "Post [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + isPublished + "]"
    }
}