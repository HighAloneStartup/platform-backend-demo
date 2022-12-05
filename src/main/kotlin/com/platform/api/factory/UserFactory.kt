package com.platform.api.factory

import com.platform.api.models.Role
import com.platform.api.models.User
import com.platform.api.payload.request.RegisterRequest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserFactory
{
    companion object
    {
        private val passwordEncoder = BCryptPasswordEncoder()

        fun newInitialUser(registerRequest: RegisterRequest, roles: ArrayList<Role>): User
        {
            return User(
                    name = registerRequest.name,
                    roles = roles,
                    email = registerRequest.email,
                    gradeYear = registerRequest.gradeYear,
                    classGroup = registerRequest.classGroup,

                    encryptedPassword = passwordEncoder.encode(registerRequest.password)
            )
        }
    }
}