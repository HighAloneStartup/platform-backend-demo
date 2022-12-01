package com.platform.api.controllers

import com.platform.api.security.services.AwsS3Service
import com.platform.api.security.services.UserDetailsServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api")
open class PhotoController (
        private val awsS3Service: AwsS3Service
){
    @PostMapping("/upload")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @Throws(IOException::class)
    open fun uploadFile(@RequestParam("images") multipartFileList: Array<MultipartFile>): ResponseEntity<*> {
        return ResponseEntity.ok(awsS3Service.upload(multipartFileList))
    }
}