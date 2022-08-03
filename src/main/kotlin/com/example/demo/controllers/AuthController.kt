package com.example.demo.controllers

import com.example.demo.models.ERole
import com.example.demo.models.Role
import com.example.demo.models.User
import com.example.demo.payload.request.LoginRequest
import com.example.demo.payload.request.SignupRequest
import com.example.demo.payload.response.JwtResponse
import com.example.demo.payload.response.MessageResponse
import com.example.demo.repository.RoleRepository
import com.example.demo.repository.UserRepository
import com.example.demo.security.jwt.JwtUtils
import com.example.demo.security.services.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors
import javax.validation.Valid

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController(
        private val userRepository: UserRepository,
        private val roleRepository: RoleRepository,
        private val authenticationManager: AuthenticationManager,
        private val encoder: PasswordEncoder,
        private val jwtUtils: JwtUtils
) {
    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest): ResponseEntity<*> {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.stream()
                .map { item: GrantedAuthority -> item.authority }
                .collect(Collectors.toList())

        return ResponseEntity.ok(JwtResponse(jwt,
                userDetails.id,
                userDetails.username,
                userDetails.email,
                roles))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: @Valid SignupRequest): ResponseEntity<*> {
        if (userRepository.existsByUsername(signUpRequest.username)) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse("Error: Username is already taken!"))
        }
        if (userRepository.existsByEmail(signUpRequest.email)) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse("Error: Email is already in use!"))
        }

        // Create new user's account
        val user = User(signUpRequest.username,
                signUpRequest.email,
                encoder.encode(signUpRequest.password))
        val strRoles = signUpRequest.roles

        val roles: MutableSet<Role> = HashSet()
        if (strRoles == null) {
            val userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow { RuntimeException("Error: Role is not found.") }
            roles.add(userRole)
        } else {
            strRoles.forEach(Consumer { role: String? ->
                when (role) {
                    "admin" -> {
                        val adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow { RuntimeException("Error: Role is not found.") }
                        roles.add(adminRole)
                    }
                    "mod" -> {
                        val modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow { RuntimeException("Error: Role is not found.") }
                        roles.add(modRole)
                    }
                    else -> {
                        val userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow { RuntimeException("Error: Role is not found.") }
                        roles.add(userRole)
                    }
                }
            })
        }
        user.roles = roles
        userRepository.save(user)
        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }
}