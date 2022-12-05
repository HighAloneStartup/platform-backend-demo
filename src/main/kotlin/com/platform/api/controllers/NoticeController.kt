package com.platform.api.controllers

import com.platform.api.models.NoticePost
import com.platform.api.payload.request.NoticeRequest
import com.platform.api.payload.request.PostRequest
import com.platform.api.payload.response.NoticeListResponse
import com.platform.api.payload.response.NoticeResponse
import com.platform.api.payload.response.PostListResponse
import com.platform.api.payload.response.PostResponse
import com.platform.api.repository.NoticeRepository
import com.platform.api.security.services.NoticeService
import com.platform.api.security.services.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api")
open class NoticeController(
        private val noticeRepository: NoticeRepository,
        private val noticeService: NoticeService
) {
    @GetMapping("/notices")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun getAllTutorials(): ResponseEntity<List<NoticeListResponse>> {
        return try {
            var noticePosts: List<NoticePost> = noticeRepository.findAll() as List<NoticePost>
            if (noticePosts.isEmpty()) {
                ResponseEntity(HttpStatus.NO_CONTENT)
            } else {
                var noticeListResponse = noticePosts.map { NoticeListResponse(it) }
                ResponseEntity(noticeListResponse, HttpStatus.OK)
            }
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/notices")
    @PreAuthorize("hasRole('ADMIN')")
    open fun deleteAllTutorials(): ResponseEntity<HttpStatus> {
        return try {
            noticeRepository.deleteAll()
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/notices/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun getPostById(@PathVariable("id") id: String?): ResponseEntity<NoticeResponse> {

        return try {
            val noticePost = noticeService.getPost(id)
            ResponseEntity.ok(noticePost)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }


    @PostMapping("/notices")
    open fun createPost(@RequestBody noticeRequest: NoticeRequest): ResponseEntity<NoticeResponse> {
        return try {
            val noticePost = noticeService.insertPost(noticeRequest);
            ResponseEntity(NoticeResponse(noticePost), HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/notices/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun deleteTutorial(@PathVariable("id") id: String?): ResponseEntity<HttpStatus> {
        return try {
            noticeRepository.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/notices/like/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun updateLike(@PathVariable("id") id: String): ResponseEntity<NoticeResponse> {
        val noticePost = noticeService.updateLike(id)

        return ResponseEntity.ok(noticePost)
    }
}