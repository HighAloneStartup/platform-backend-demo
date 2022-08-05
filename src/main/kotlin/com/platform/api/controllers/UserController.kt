package com.platform.api.controllers

import com.platform.api.repository.GradeRepository
import com.platform.api.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/users")
class UserController(
        private val userRepository: UserRepository
) {

    @GetMapping("/")
    fun getStudent(@RequestParam("objectid") objectid: ObjectId) {
        //token 검증. 
        var user = userRepository.findById(objectid)
        // user 출력.
    }

    @GetMapping("/classgroup")
    fun getClassGroupStudents(@RequestParam("classgroup") classGroup: Int) {

    }

}