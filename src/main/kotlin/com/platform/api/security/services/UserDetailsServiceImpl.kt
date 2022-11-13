package com.platform.api.security.services

import com.platform.api.models.ERole
import com.platform.api.models.Role
import com.platform.api.models.User
import com.platform.api.repository.RoleRepository
import com.platform.api.repository.UserRepository
import com.platform.api.security.services.UserDetailsImpl.Companion.build
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.HashSet
import java.util.function.Supplier
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

    fun saveUserByUserDetails(userDetailsImpl: UserDetailsImpl): User
    {
        val user = buildUser(userDetailsImpl)

        return userRepository.save(user)
    }

    private fun buildUser(userDetails: UserDetailsImpl): User
    {
        val roles: MutableSet<Role> = HashSet<Role>()
        for (strRoles in userDetails.authorities)
        {
            val userRole: Role = roleRepository.findByName(ERole.valueOf(strRoles.authority))
                    ?.orElseThrow(Supplier { RuntimeException("Error: Role is not found.") })!!
            roles.add(userRole)
        }

        return User(
                userDetails.id,
                userDetails.name,
                userDetails.email,
                userDetails.password,
                roles,
                userDetails.gradeYear,
                userDetails.classGroup,
                userDetails.attendanceNumber,
                userDetails.generationNumber,
                userDetails.studentNumber,
                userDetails.birthday,
                userDetails.phoneNumber,
                userDetails.photoUrl
        )
    }
}