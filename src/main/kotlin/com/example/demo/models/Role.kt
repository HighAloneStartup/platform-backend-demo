package com.example.demo.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "roles")
class Role {
    @Id
    var id: String? = null
    var name: ERole? = null

    constructor() {}
    constructor(name: ERole?) {
        this.name = name
    }



}