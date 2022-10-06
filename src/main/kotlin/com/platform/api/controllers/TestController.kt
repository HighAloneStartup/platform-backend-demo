package com.platform.api.controllers

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/test")
open class TestController {
    @GetMapping("/all")
    fun allAccess(): String {
        return "Public Content."
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('STUDENT')")
    fun userAccess(): String {
        return "User Content."
    }

    @GetMapping("/student")
    fun studentAccess(): String {
        return "Student Board."
    }

    @GetMapping("/admin")
    fun adminAccess(): String {
        return "Admin Board."
    }
}