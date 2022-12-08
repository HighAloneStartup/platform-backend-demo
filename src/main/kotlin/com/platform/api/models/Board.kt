package com.platform.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.ArrayList

@Document(collection = "boards")
class Board (
    @Id
    val id: String = String(),
    val name: String = String(),
    @DBRef
    val roles: ArrayList<Role> = ArrayList<Role>()
)