package com.platform.api.controllers

import com.platform.api.models.Board
import com.platform.api.repository.PostRepository
import com.platform.api.models.BoardPost
import com.platform.api.models.Role
import com.platform.api.payload.request.BoardRequest
import com.platform.api.payload.request.PostRequest
import com.platform.api.payload.response.PostListResponse
import com.platform.api.payload.response.PostResponse
import com.platform.api.repository.BoardRepository
import com.platform.api.repository.RoleRepository
import com.platform.api.security.services.BoardService
import com.platform.api.security.services.PostService
import com.platform.api.security.services.UserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api")
open class BoardController(
        private val userDetailsServiceImpl: UserDetailsServiceImpl,
        private val roleRepository: RoleRepository,
        private val boardRepository: BoardRepository,
        private val boardService: BoardService,
        private val postRepository: PostRepository,
        private val postService: PostService
)
{
    @PostMapping("/pushrole")
    open fun postBoardRole(@RequestBody boardRequest: BoardRequest): ResponseEntity<*>
    {
        val roles : ArrayList<Role> = ArrayList<Role>()
        for(inputRoleString in boardRequest.roles)
        {
            val role = roleRepository.findByName(inputRoleString)
            roles.add(role!!)
        }

        val board = Board("", boardRequest.name, roles)

        boardRepository.save(board)

        return ResponseEntity.ok(board)
    }


    @GetMapping("/boards/{name}")
    open fun getAllPosts(@PathVariable("name") boardName: String): ResponseEntity<List<PostListResponse>>
    {
        try
        {
            val boardRoles = boardService.getBoardRolesByName(boardName)
            val hasRole = userDetailsServiceImpl.checkUserRole(boardRoles)
            if(hasRole)
            {
                postRepository.setCollectionName(boardName)
                var boardPosts: List<BoardPost> = postRepository.findAll() as List<BoardPost>
                if (boardPosts.isEmpty())
                {
                    return ResponseEntity(HttpStatus.NO_CONTENT)
                }
                else
                {
                    var postListResponse = boardPosts.map{ PostListResponse(it) }
                    return ResponseEntity(postListResponse, HttpStatus.OK)
                }
            }
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/boards/{name}/{id}")
    open fun getPostById(@PathVariable("name") boardName: String, @PathVariable("id") id: String?): ResponseEntity<PostResponse>
    {
        try{
            val boardRoles = boardService.getBoardRolesByName(boardName)
            val hasRole = userDetailsServiceImpl.checkUserRole(boardRoles)
            if(hasRole)
            {
                val postData = postRepository.findById(id)
                return if (postData.isPresent)
                {
                    var postResponse = PostResponse(postData.get())
                    ResponseEntity(postResponse, HttpStatus.OK)
                }
                else
                {
                    ResponseEntity(HttpStatus.NOT_FOUND)
                }
            }
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/boards/{name}")
    open fun createPost(@PathVariable("name") boardName : String, @RequestBody postRequest: PostRequest): ResponseEntity<PostResponse>
    {
        try
        {
            val boardRoles = boardService.getBoardRolesByName(boardName)
            val hasRole = userDetailsServiceImpl.checkUserRole(boardRoles)
            if(hasRole)
            {
                val boardPost = postService.insertPost(boardName, postRequest);
                return ResponseEntity(PostResponse(boardPost), HttpStatus.CREATED)
            }
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/boards/{name}/{id}")
    open fun updatePost(@PathVariable("name") boardName: String, @PathVariable("id") id: String, @RequestBody postRequest: PostRequest): ResponseEntity<PostResponse>
    {
        try{
            val boardRoles = boardService.getBoardRolesByName(boardName)
            val hasRole = userDetailsServiceImpl.checkUserRole(boardRoles)
            if(hasRole)
            {
                val boardPost = postService.updatePost(id, postRequest)
                return ResponseEntity.ok(PostResponse(boardPost))
            }
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/boards/{name}/{id}")
    open fun deletePost(@PathVariable("name") boardName: String, @PathVariable("id") id: String?): ResponseEntity<HttpStatus>
    {
        try
        {
            val boardRoles = boardService.getBoardRolesByName(boardName)
            val hasRole = userDetailsServiceImpl.checkUserRole(boardRoles)
            if(hasRole)
            {
                postRepository.deleteById(id)
                return ResponseEntity(HttpStatus.NO_CONTENT)
            }
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/boards/{name}/{id}/like")
    open fun updateLike(@PathVariable("name") boardName: String, @PathVariable("id") id: String): ResponseEntity<PostResponse>
    {
        try {
            val boardRoles = boardService.getBoardRolesByName(boardName)
            val hasRole = userDetailsServiceImpl.checkUserRole(boardRoles)
            if (hasRole) {
                val boardPost = postService.updateLike(id)

                return ResponseEntity.ok(boardPost)
            }
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        } catch (e: Exception)
        {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}