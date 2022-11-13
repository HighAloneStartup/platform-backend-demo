package com.platform.api.controllers

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/timetable")
open class TimeTableController
{
    @GetMapping("/all")
    fun allAccess(): String
    {
        return "Public Content."
    }
}