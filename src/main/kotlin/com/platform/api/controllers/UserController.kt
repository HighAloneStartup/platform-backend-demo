package com.platform.api.controllers

import com.platform.api.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/users")
open class UserController(
        private val userRepository: UserRepository
)
{

    @GetMapping("/")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun getStudent(@RequestParam("objectid") objectid: ObjectId)
    {
        var user = userRepository.findById(objectid)
        ResponseEntity.ok(user)
    }

//    @GetMapping("/")
//    open fun getGradeStudents(@RequestParam("gradeYear") classGroup: Int)
//    {
//
//    }

}