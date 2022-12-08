package com.platform.api.controllers

import com.platform.api.models.BoardPost
import com.platform.api.payload.request.PostRequest
import com.platform.api.payload.response.ClassGroupPostListResponse
import com.platform.api.payload.response.PostResponse
import com.platform.api.repository.ClassGroupRepository
import com.platform.api.repository.PostRepository
import com.platform.api.security.services.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api")
open class ClassGroupController(
        private val postService: PostService,
        private val postRepository : PostRepository,
        private val classGroupRepository: ClassGroupRepository
)
{
//    @PostMapping("/pushclass")
//    open fun postBoardRole(@RequestBody classGroupRequest: ClassGroupRequest): ResponseEntity<*>
//    {
//        val classGroup = ClassGroup(name = classGroupRequest.name,
//                year = classGroupRequest.year,
//        grade = classGroupRequest.grade,
//        classGroup = classGroupRequest.classGroup
//        )
//
//        //이따가 반 애들 나눠
//
//        classGroupRepository.save(classGroup)
//
//        return ResponseEntity.ok(classGroup)
//    }

    @GetMapping("/classes/{name}")
    open fun getAllPosts(@PathVariable("name") name: String): ResponseEntity<List<ClassGroupPostListResponse>>
    {
        try
        {
            postRepository.setCollectionName(name)
            val classGroupPosts: List<BoardPost> = postRepository.findAll() as List<BoardPost>
            if (classGroupPosts.isEmpty())
            {
                return ResponseEntity(HttpStatus.NO_CONTENT)
            }
            else
            {
                val classGroupPostListResponse = classGroupPosts.map{ ClassGroupPostListResponse(it) }
                return ResponseEntity(classGroupPostListResponse, HttpStatus.OK)
            }
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/classes/{name}/{id}")
    open fun getPostById(@PathVariable("name") boardName: String, @PathVariable("id") id: String?): ResponseEntity<PostResponse>
    {
        try{
            val postData = postRepository.findById(id)
            return if (postData.isPresent)
            {
                val postResponse = PostResponse(postData.get())
                ResponseEntity(postResponse, HttpStatus.OK)
            }
            else
            {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/classes/{name}")
    open fun createPost(@PathVariable("name") boardName : String, @RequestBody postRequest: PostRequest): ResponseEntity<PostResponse>
    {
        try
        {
            val boardPost = postService.insertPost(boardName, postRequest);
            return ResponseEntity(PostResponse(boardPost), HttpStatus.CREATED)
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/classes/{name}/{id}")
    open fun updatePost(@PathVariable("name") boardName: String, @PathVariable("id") id: String, @RequestBody postRequest: PostRequest): ResponseEntity<PostResponse>
    {
        try{
            val boardPost = postService.updatePost(id, postRequest)
            return ResponseEntity.ok(PostResponse(boardPost))
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/classes/{name}/{id}")
    open fun deletePost(@PathVariable("name") boardName: String, @PathVariable("id") id: String?): ResponseEntity<HttpStatus>
    {
        try
        {
            postRepository.deleteById(id)
            return ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/classes/{name}/{id}/like")
    open fun updateLike(@PathVariable("name") boardName: String, @PathVariable("id") id: String): ResponseEntity<PostResponse>
    {
        try {
            val boardPost = postService.updateLike(id)

            return ResponseEntity.ok(boardPost)
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}