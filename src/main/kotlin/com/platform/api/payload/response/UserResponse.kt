package com.platform.api.payload.response

import com.platform.api.models.User
import org.bson.types.ObjectId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.stream.Collectors

class UserResponse(
user: User
)
{
    val name: String = user.name
    val email: String = user.email
    val authorities: Collection<GrantedAuthority> = user.roles.stream()
            .map { role -> SimpleGrantedAuthority(role.name?.name) }
            .collect(Collectors.toList())
    val gradeYear: Int? = user.gradeYear
    val classGroup: Int? = user.classGroup
}