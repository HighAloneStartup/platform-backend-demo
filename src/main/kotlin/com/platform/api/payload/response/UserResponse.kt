package com.platform.api.payload.response

import com.platform.api.models.Role
import com.platform.api.models.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*
import java.util.stream.Collectors

class UserResponse(
user: User
)
{
    val uid: ObjectId = user.uid
    val name: String = user.name
    val authorities: Collection<GrantedAuthority> = user.roles.stream()
            .map { role -> SimpleGrantedAuthority(role.name?.name) }
            .collect(Collectors.toList())
    val email: String = user.email

    val gradeYear: Int? = user.gradeYear
    val classGroup: Int? = user.classGroup

    val attendanceNumber: Int? = user.attendanceNumber

    val generationNumber: Int? = user.generationNumber
    val studentNumber: Int? = user.studentNumber
    val birthday: Date? = user.birthday
    var phoneNumber: String? = user.phoneNumber

    val photoUrl: String? = user.photoUrl
}