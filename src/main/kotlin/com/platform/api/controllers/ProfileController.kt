package com.platform.api.controllers

import com.platform.api.models.User
import com.platform.api.payload.request.ProfileRequest
import com.platform.api.repository.UserRepository
import com.platform.api.security.services.UserDetailsServiceImpl
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/profile")
class ProfileController(
        private val userRepository: UserRepository,
        private var userDetailsService: UserDetailsServiceImpl

)
{
    @GetMapping("/")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun getUserInfo(): ResponseEntity<UserDetails>
    {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication

        val userDetails = userDetailsService.loadUserByUsername(authentication.name)

        return ResponseEntity.ok(userDetails)
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun updateUserInfo(@RequestBody profileRequest: @Valid ProfileRequest): ResponseEntity<User>
    {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val user: User? = userRepository.findByEmail(authentication.name)

        return if (user != null)
        {
            user.phoneNumber = profileRequest.phoneNumber
            user.photoUrl = profileRequest.photoUrl
            ResponseEntity.ok(userRepository.save(user))
        }
        else
        {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}