package com.platform.api.controllers

import com.platform.api.models.BoardPost
import com.platform.api.models.ClassGroup
import com.platform.api.models.Role
import com.platform.api.payload.request.ClassGroupRequest
import com.platform.api.payload.request.CommentRequest
import com.platform.api.payload.request.PostRequest
import com.platform.api.payload.response.ClassGroupPostListResponse
import com.platform.api.payload.response.PostResponse
import com.platform.api.repository.ClassGroupRepository
import com.platform.api.repository.PostRepository
import com.platform.api.repository.RoleRepository
import com.platform.api.security.services.ClassGroupService
import com.platform.api.security.services.CommentService
import com.platform.api.security.services.PostService
import com.platform.api.security.services.UserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api")
open class ClassGroupController(
        private val userDetailsServiceImpl: UserDetailsServiceImpl,
        private val postService: PostService,
        private val postRepository : PostRepository,
        private val commentService: CommentService,
        private val classGroupRepository: ClassGroupRepository,
        private val roleRepository:RoleRepository,
        private val classGroupService: ClassGroupService
)
{
    @PostMapping("/pushclass")
    open fun postBoardRole(@RequestBody classGroupRequest: ClassGroupRequest): ResponseEntity<*>
    {
        val roles : ArrayList<Role> = ArrayList<Role>()
        for(inputRoleString in classGroupRequest.roles)
        {
            val role = roleRepository.findByName(inputRoleString)
            roles.add(role!!)
        }

        val classGroup = ClassGroup(
                name = classGroupRequest.name,
                gradeYear = classGroupRequest.gradeYear,
                classGroup = classGroupRequest.classGroup,
                roles = roles
        )

        classGroupRepository.save(classGroup)

        return ResponseEntity.ok(classGroup)
    }

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
                val user = userDetailsServiceImpl.loadMyUser();
                val postResponse = PostResponse(postData.get(), user.uid)
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
            val boardRoles = classGroupService.getClassGroupRolesByName(boardName)
            val hasRole = userDetailsServiceImpl.checkUserRole(boardRoles)
            if(hasRole)
            {
                val user = userDetailsServiceImpl.loadMyUser();
                val boardPost = postService.insertPost(boardName, postRequest);
                return ResponseEntity(PostResponse(boardPost, user.uid), HttpStatus.CREATED)
            }
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/classes/{name}/{id}")
    open fun updatePost(@PathVariable("name") boardName: String, @PathVariable("id") id: String, @RequestBody postRequest: PostRequest): ResponseEntity<PostResponse>
    {
        try{
            val user = userDetailsServiceImpl.loadMyUser();
            val boardPost = postService.updatePost(boardName, id, postRequest)
            return ResponseEntity.ok(PostResponse(boardPost, user.uid))
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
            val boardPost = postService.updateLike(boardName, id)

            return ResponseEntity.ok(boardPost)
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/classes/{name}/{id}/comment")
    open fun updateLike(@PathVariable("name") boardName: String,
                        @PathVariable("id") id: String,
                        @RequestBody commentRequest: CommentRequest): ResponseEntity<PostResponse>
    {
        try {
            var comment = commentService.postComment(commentRequest)
            comment = commentService.getComment(comment.description)
            val boardPost = postService.updateComment(boardName, id, comment)
            return ResponseEntity.ok(boardPost)
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}