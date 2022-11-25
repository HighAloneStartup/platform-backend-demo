package com.platform.api.security.services

import com.platform.api.models.User
import com.platform.api.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws

@Service
open class UserDetailsServiceImpl(
        private val userRepository: UserRepository
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
        return user;
    }
}