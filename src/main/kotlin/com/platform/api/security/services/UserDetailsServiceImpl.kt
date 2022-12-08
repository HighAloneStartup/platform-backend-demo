package com.platform.api.security.services

import com.platform.api.models.Role
import com.platform.api.models.User
import com.platform.api.payload.request.ProfileRequest
import com.platform.api.repository.RoleRepository
import com.platform.api.repository.UserRepository
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.Throws

@Service
open class UserDetailsServiceImpl(
        private val userRepository: UserRepository,
        private val roleRepository: RoleRepository
) : UserDetailsService
{
    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails
    {
        val user = userRepository.findByEmail(email)
                ?: throw UsernameNotFoundException("User Not Found with email: $email")

        return UserDetailsImpl.build(user)
    }

    open fun loadMyUser(): User
    {
        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val user = userRepository.findById(userDetailsImpl.id).get()
        return user
    }

    open fun updateMyUser(profileRequest: ProfileRequest): User
    {
        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val user = userRepository.findById(userDetailsImpl.id).get()

        val roles = ArrayList<Role>()

        for(roleString in profileRequest.roles!!)
        {
            var role = roleRepository.findByName(roleString)
            roles.add(role!!)
        }

        if(profileRequest.roles.size == 0)
        {
            var role = roleRepository.findByName("ROLE_STUDENT")!!
            roles.add(role)
        }

        val updatedUser = User(
                uid = user.uid,
                name = user.name,
                roles = roles,
                email = user.email,
                encryptedPassword = user.encryptedPassword,
                gradeYear = user.gradeYear,
                classGroup = user.classGroup,
                attendanceNumber = user.attendanceNumber,
                generationNumber = user.generationNumber,
                studentNumber = user.studentNumber,
                birthday = user.birthday,
                phoneNumber = profileRequest.phoneNumber,
                photoUrl = profileRequest.photoUrl

        )

        userRepository.save(updatedUser)

        return updatedUser
    }

    open fun updateUser(uid:String, profileRequest: ProfileRequest): User
    {
        val user = userRepository.findById(uid).get()

        val roles = ArrayList<Role>()

        for(roleString in profileRequest.roles!!)
        {
            var role = roleRepository.findByName(roleString)
            roles.add(role!!)
        }

        if(profileRequest.roles.size == 0)
        {
            var role = roleRepository.findByName("ROLE_STUDENT")!!
            roles.add(role)
        }

        val updatedUser = User(
                uid = user.uid,
                name = user.name,
                roles = roles,
                email = user.email,
                encryptedPassword = user.encryptedPassword,
                gradeYear = profileRequest.gradeYear,
                classGroup = profileRequest.classGroup,
                attendanceNumber = user.attendanceNumber,
                generationNumber = user.generationNumber,
                studentNumber = user.studentNumber,
                birthday = user.birthday,
                phoneNumber = profileRequest.phoneNumber,
                photoUrl = profileRequest.photoUrl

        )

        return userRepository.save(updatedUser)
    }

    open fun checkUserRole(roles: ArrayList<Role>) : Boolean
    {
        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val user = userRepository.findById(userDetailsImpl.id).get()
        val userRoles = user.roles
        for (userRole in user.roles)
        {
            for(role in roles)
            {
                if(userRole.id == role.id)
                {
                    return true;
                }

            }
        }

        return false;
    }

}