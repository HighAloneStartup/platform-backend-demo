package com.platform.api.controllers

import com.platform.api.factory.UserFactory
import com.platform.api.models.User
import com.platform.api.payload.request.LoginRequest
import com.platform.api.payload.request.RegisterRequest
import com.platform.api.payload.response.JwtResponse
import com.platform.api.payload.response.MessageResponse
import com.platform.api.repository.UserRepository
import com.platform.api.security.jwt.JwtUtils
import com.platform.api.security.services.UserDetailsImpl
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController(
        private val userRepository: UserRepository,
        private val authenticationManager: AuthenticationManager,
        private val jwtUtils: JwtUtils
) {
    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest): ResponseEntity<*> {

        val user = userRepository.findByEmail(loginRequest.email)

        return if (user == null) {
            ResponseEntity.notFound().build<User>()
        } else {
            val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password))
            SecurityContextHolder.getContext().authentication = authentication

            val token = jwtUtils.generateJwtToken(authentication)

            val userDetails = authentication.principal as UserDetailsImpl
            val response = JwtResponse(token,
                    userDetails.id,
                    userDetails.name,
                    userDetails.email)

            ResponseEntity.ok(response)
        }
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody registerRequest: @Valid RegisterRequest): ResponseEntity<*> {
        if (userRepository.existsByEmail(registerRequest.email)) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse("Error: Email is already in use!"))
        }

        val user = UserFactory.newInitialUser(registerRequest)
        userRepository.save(user)

        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }
}