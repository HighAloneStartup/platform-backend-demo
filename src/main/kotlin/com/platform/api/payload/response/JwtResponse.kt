package com.platform.api.payload.response

import org.bson.types.ObjectId

class JwtResponse(
        val accessToken: String,
        val id: String,
        val name: String,
        val email: String,
        val roles: List<String>
)
{
    val tokenType = "Bearer"
}