package com.platform.api.controllers

import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/classgroup")
open class ClassGroupController
{
    @GetMapping("/first")
    open fun getFirstClassGroupCounts(): Int
    {
        return 13
    }

    @GetMapping("/second")
    fun getSecondClassGroupCounts(): Int
    {
        return 13
    }

    @GetMapping("/third")
    fun getThirdClassGroupCounts(): Int
    {
        return 13
    }
}