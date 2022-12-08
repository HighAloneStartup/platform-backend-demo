package com.platform.api.controllers

import com.platform.api.models.User
import com.platform.api.payload.request.PostRequest
import com.platform.api.payload.request.ProfileRequest
import com.platform.api.payload.response.UserResponse
import com.platform.api.repository.UserRepository
import com.platform.api.security.services.PostService
import com.platform.api.security.services.UserDetailsImpl
import com.platform.api.security.services.UserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/members")
open class MemberController(
        private val userRepository: UserRepository,
        private val userDetailsService: UserDetailsServiceImpl
)
{
    @GetMapping("/")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun getUsers(@RequestParam(required = false)
                 gradeYear: Int?,
                classGroup: Int?): ResponseEntity<*>
    {
        var userResponse: List<UserResponse>
        if(gradeYear == null && classGroup == null)
        {
            val user = userRepository.findAll() as List<User>
            userResponse = user.map{ UserResponse(it) }

        }
        else if(classGroup == null)
        {
            val user = gradeYear?.let { userRepository.findByGradeYear(it) } as List<User>
            userResponse = user.map { UserResponse(it) }
        }
        else if(gradeYear == null)
        {
            val user = userRepository.findByClassGroup(classGroup) as List<User>
            userResponse = user.map{ UserResponse(it)}
        }
        else
        {
            val user = userRepository.findByGradeYearAndClassGroup(gradeYear, classGroup) as List<User>
            userResponse = user.map{ UserResponse(it)}
        }

        return if (userResponse.isEmpty())
        {
            ResponseEntity.notFound().build<User>()
        }
        else ResponseEntity(userResponse, HttpStatus.OK)
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun getMine() : ResponseEntity<*>
    {
        val user = userDetailsService.loadMyUser()
        var userResponse = UserResponse(user)

        return ResponseEntity.ok(userResponse)
    }

    @PutMapping("/mine")
    open fun updateMine(@RequestBody profileRequest: ProfileRequest) : ResponseEntity<*>
    {
        val user = userDetailsService.updateMyUser(profileRequest)
        var userResponse = UserResponse(user)

        return ResponseEntity.ok(userResponse)
    }

    @PutMapping("/{id}")
    open fun updateUser(@PathVariable("id") uid: String, @RequestBody profileRequest: ProfileRequest) : ResponseEntity<*>
    {
        val user = userDetailsService.updateUser(uid, profileRequest)
        var userResponse = UserResponse(user)

        return ResponseEntity.ok(userResponse)
    }
}