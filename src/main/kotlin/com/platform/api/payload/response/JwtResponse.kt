package com.platform.api.payload.response

import org.bson.types.ObjectId

class JwtResponse(
        val accessToken: String,
        val id: ObjectId,
        val name: String,
        val email: String
) {
    val tokenType = "Bearer"
}