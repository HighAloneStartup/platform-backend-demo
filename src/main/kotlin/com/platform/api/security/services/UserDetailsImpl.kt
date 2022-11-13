package com.platform.api.security.services

import com.platform.api.models.User
import org.bson.types.ObjectId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors


class UserDetailsImpl(
        val id: ObjectId,
        val name: String,
        val email: String,
        private val password: String,
        private val authorities: Collection<GrantedAuthority>,
        val gradeYear: Int? = null,
        val classGroup: Int? = null,
        val attendanceNumber: Int? = null,

        val generationNumber: Int? = null,
        val studentNumber: Int? = null,
        val birthday: Date? = null,
        var phoneNumber: String? = null,

        val photoUrl: String? = null
) : UserDetails
{


    override fun getAuthorities(): Collection<GrantedAuthority>
    {
        return authorities
    }

    override fun getPassword(): String
    {
        return password
    }

    override fun getUsername(): String?
    {
        return email
    }

    override fun isAccountNonExpired(): Boolean
    {
        return true
    }

    override fun isAccountNonLocked(): Boolean
    {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean
    {
        return true
    }

    override fun isEnabled(): Boolean
    {
        return true
    }

    override fun equals(o: Any?): Boolean
    {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val user = o as UserDetailsImpl
        return id == user.id
    }

    companion object
    {
        private const val serialVersionUID = 1L
        fun build(user: User): UserDetailsImpl
        {
            val authorities: List<GrantedAuthority> = user.roles.stream()
                    .map { role -> SimpleGrantedAuthority(role.name?.name) }
                    .collect(Collectors.toList())

            return UserDetailsImpl(
                    user.uid,
                    user.name,
                    user.email,
                    user.encryptedPassword,
                    authorities,
                    user.gradeYear,
                    user.classGroup,
                    user.attendanceNumber,
                    user.generationNumber,
                    user.studentNumber,
                    user.birthday,
                    user.phoneNumber,
                    user.photoUrl
            )
        }
    }
}