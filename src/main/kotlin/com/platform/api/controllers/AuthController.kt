package com.platform.api.controllers

import com.platform.api.factory.UserFactory
import com.platform.api.models.ERole
import com.platform.api.models.Role
import com.platform.api.models.User
import com.platform.api.payload.request.LoginRequest
import com.platform.api.payload.request.RegisterRequest
import com.platform.api.payload.response.JwtResponse
import com.platform.api.payload.response.MessageResponse
import com.platform.api.repository.RoleRepository
import com.platform.api.repository.UserRepository
import com.platform.api.security.jwt.JwtUtils
import com.platform.api.security.services.UserDetailsImpl
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.function.Supplier
import java.util.stream.Collectors
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
open class AuthController(
        private val userRepository: UserRepository,
        private val roleRepository: RoleRepository,
        private val authenticationManager: AuthenticationManager,
        private val jwtUtils: JwtUtils
)
{
    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest): ResponseEntity<*>
    {

        val user = userRepository.findByEmail(loginRequest.email)

        return if (user == null)
        {
            ResponseEntity.notFound().build<User>()
        } else
        {
            val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password))
            SecurityContextHolder.getContext().authentication = authentication

            val token = jwtUtils.generateJwtToken(authentication)

            val userDetails = authentication.principal as UserDetailsImpl
            val roles = userDetails.authorities.stream()
                    .map { item -> item.authority }
                    .collect(Collectors.toList())

            val response = JwtResponse(token,
                    userDetails.id,
                    userDetails.name,
                    userDetails.email,
                    roles)

            ResponseEntity.ok(response)
        }
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody registerRequest: @Valid RegisterRequest): ResponseEntity<*>
    {
        if (userRepository.existsByEmail(registerRequest.email))
        {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse("Error: Email is already in use!"))
        }

        val strRoles: Set<String>? = registerRequest.roles
        val roles: MutableSet<Role> = HashSet<Role>()

        if (strRoles == null)
        {
            val userRole: Role = roleRepository.findByName(ERole.ROLE_USER)
                    ?.orElseThrow(Supplier { RuntimeException("Error: Role is not found.") })!!
            roles.add(userRole)
        }
        else
        {
            for(strRole in strRoles)
            {
                when (strRole)
                {
                    "admin" ->
                    {
                        val adminRole: Role? = roleRepository.findByName(ERole.ROLE_ADMIN)
                                ?.orElseThrow(Supplier { RuntimeException("Error: Role is not found.") })
                        if (adminRole != null)
                        {
                            roles.add(adminRole)
                        }
                    }
                    "mod" ->
                    {
                        val modRole: Role? = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                ?.orElseThrow(Supplier { RuntimeException("Error: Role is not found.") })
                        if (modRole != null)
                        {
                            roles.add(modRole)
                        }
                    }
                    else ->
                    {
                        val userRole: Role? = roleRepository.findByName(ERole.ROLE_USER)
                                ?.orElseThrow(Supplier { RuntimeException("Error: Role is not found.") })
                        if (userRole != null)
                        {
                            roles.add(userRole)
                        }
                    }
                }
            }
        }


        val user = UserFactory.newInitialUser(registerRequest, roles)

        userRepository.save(user)

        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }
}