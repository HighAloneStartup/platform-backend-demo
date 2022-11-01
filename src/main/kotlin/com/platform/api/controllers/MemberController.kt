package com.platform.api.controllers

import com.platform.api.models.ClassGroup
import com.platform.api.models.GradeYear
import com.platform.api.models.User
import com.platform.api.repository.UserRepository
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun getUsers(@RequestParam(required = false)
                 gradeYear: GradeYear?,
                classGroup: ClassGroup?): ResponseEntity<*>
    {
        var users: List<User>

        if(gradeYear == null && classGroup == null)
        {
            users = userRepository.findAll() as List<User>
        }
        else if(classGroup == null)
        {
            users = userRepository.findByGradeYear(gradeYear!!) as List<User>
        }
        else if(gradeYear == null)
        {
            users = userRepository.findByClassGroup(classGroup) as List<User>
        }
        else
        {
            users = userRepository.findByGradeYearAndClassGroup(gradeYear, classGroup) as List<User>
        }

        return if (users.isEmpty())
        {
            ResponseEntity.notFound().build<User>()
        }
        else ResponseEntity.ok(users)
    }
}