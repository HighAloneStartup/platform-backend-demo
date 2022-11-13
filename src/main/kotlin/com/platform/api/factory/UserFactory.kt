package com.platform.api.factory

import com.platform.api.models.ERole
import com.platform.api.models.Role
import com.platform.api.models.User
import com.platform.api.payload.request.RegisterRequest
import com.platform.api.repository.RoleRepository
import com.platform.api.security.services.UserDetailsImpl
import org.apache.logging.log4j.util.Supplier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserFactory(

)
{
    companion object
    {
        private val passwordEncoder = BCryptPasswordEncoder()

        fun newInitialUser(registerRequest: RegisterRequest, roles: MutableSet<Role>): User
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