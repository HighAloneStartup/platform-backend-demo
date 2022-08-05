package com.platform.api.factory

import com.platform.api.models.User
import com.platform.api.models.UserRole
import com.platform.api.payload.request.RegisterRequest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserFactory {
    companion object {
        private val passwordEncoder = BCryptPasswordEncoder()

        fun newInitialUser(registerRequest: RegisterRequest): User {
            return User(
                    name = registerRequest.name,
                    role = UserRole.ROLE_STUDENT,
                    email = registerRequest.email,
                    encryptedPassword = passwordEncoder.encode(registerRequest.password)
            )
        }
    }
}