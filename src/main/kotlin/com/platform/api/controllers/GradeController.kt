package com.platform.api.controllers

import com.platform.api.models.Grade
import com.platform.api.repository.GradeRepository
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/grades")
class GradeController(
        private val gradeRepository: GradeRepository
) {

    @GetMapping
    fun getAll(): MutableList<Grade> {
        return gradeRepository.findAll()
    }

    //학년 반 넣으면 각반의 학생을 돌려주는 api
    @GetMapping("/classgroup")
    fun getClassGroupStudents(@RequestParam("classgroup") classGroup: Int) {

    }

}