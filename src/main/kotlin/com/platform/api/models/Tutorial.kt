package com.platform.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "tutorials")
class Tutorial
{
    @Id
    val id: String? = null
    var title: String? = null
    var description: String? = null
    var isPublished = false

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
        return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + isPublished + "]"
    }
}