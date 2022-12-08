package com.platform.api.controllers

import com.platform.api.models.Timetable
import com.platform.api.payload.request.BoardRequest
import com.platform.api.payload.request.TimetableRequest
import com.platform.api.payload.response.TimetableResponse
import com.platform.api.repository.RoleRepository
import com.platform.api.repository.UserRepository
import com.platform.api.security.jwt.JwtUtils
import com.platform.api.security.services.TimetableService
import com.platform.api.security.services.UserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api")
open class TimetableController(
        private val timetableService: TimetableService,
        private val userDetailsServiceImpl: UserDetailsServiceImpl
){
    @PostMapping("/timetable")
    open fun postTimeTable(@RequestBody timetableRequest: TimetableRequest) : ResponseEntity<TimetableResponse>
    {
        return try
        {
            val timetable = timetableService.postTimetable(timetableRequest)
            ResponseEntity.ok(TimetableResponse(timetable))
        } catch (e: Exception)
        {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/timetable")
    open fun getTimeTable() : ResponseEntity<TimetableResponse>
    {
        return try
        {
            val user = userDetailsServiceImpl.loadMyUser()
            val timetable = timetableService.getTimetable(user)
            ResponseEntity.ok(TimetableResponse(timetable))
        } catch (e: Exception)
        {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}