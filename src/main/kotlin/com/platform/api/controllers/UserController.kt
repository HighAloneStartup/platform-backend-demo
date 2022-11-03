package com.platform.api.controllers

import com.platform.api.models.User
import com.platform.api.payload.request.RegisterRequest
import com.platform.api.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/users")
open class UserController(
        private val userRepository: UserRepository
)
{

    @GetMapping("/")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun changeUserInfo(@RequestBody registerRequest: @Valid RegisterRequest)
    {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val userDetails: User = authentication.getPrincipal() as User
        userDetails.phoneNumber = "010"

        userRepository.save(userDetails)
    }
}