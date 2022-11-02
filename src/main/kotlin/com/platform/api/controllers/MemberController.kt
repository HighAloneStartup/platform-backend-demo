package com.platform.api.controllers

import com.platform.api.models.User
import com.platform.api.payload.response.UserResponse
import com.platform.api.repository.UserRepository
import com.platform.api.security.services.UserDetailsImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/members")
open class MemberController(
        private val userRepository: UserRepository
)
{
    @GetMapping("/")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun getUsers(@RequestParam(required = false)
                 gradeYear: Int?,
                classGroup: Int?): ResponseEntity<*>
    {
        var userDetail: List<UserResponse>
        if(gradeYear == null && classGroup == null)
        {
            val user = userRepository.findAll() as List<User>
            userDetail = user.map{ UserResponse(it) }

        }
        else if(classGroup == null)
        {
            val user = gradeYear?.let { userRepository.findByGradeYear(it) } as List<User>
            userDetail = user.map { UserResponse(it) }
        }
        else if(gradeYear == null)
        {
            val user = userRepository.findByClassGroup(classGroup) as List<User>
            userDetail = user.map{ UserResponse(it)}
        }
        else
        {
            val user = userRepository.findByGradeYearAndClassGroup(gradeYear, classGroup) as List<User>
            userDetail = user.map{ UserResponse(it)}
        }

        return if (userDetail.isEmpty())
        {
            ResponseEntity.notFound().build<User>()
        }
        else ResponseEntity(userDetail, HttpStatus.OK)
    }
}