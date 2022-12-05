package com.platform.api.controllers

import com.platform.api.repository.BoardRepository
import com.platform.api.models.BoardPost
import com.platform.api.payload.request.PostRequest
import com.platform.api.payload.response.PostListResponse
import com.platform.api.payload.response.PostResponse
import com.platform.api.payload.response.UserResponse
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
        private val boardRepository: BoardRepository,
        private val postService: PostService
)
{
    @GetMapping("/boards")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun getAllTutorials(): ResponseEntity<List<PostListResponse>>
    {
        return try
        {
            var boardPosts: List<BoardPost> = boardRepository.findAll() as List<BoardPost>
            if (boardPosts.isEmpty())
            {
                ResponseEntity(HttpStatus.NO_CONTENT)
            }
            else
            {
                var postListResponse = boardPosts.map{ PostListResponse(it) }
                ResponseEntity(postListResponse, HttpStatus.OK)
            }
        } catch (e: Exception)
        {
            System.out.println(e)

            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/boards")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun deleteAllTutorials(): ResponseEntity<HttpStatus>
    {
        return try
        {
            boardRepository.deleteAll()
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception)
        {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }



    @GetMapping("/boards/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun getPostById(@PathVariable("id") id: String?): ResponseEntity<PostResponse>
    {
        val postData = boardRepository.findById(id)
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

    @PostMapping("/boards")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun createPost(@RequestBody postRequest: PostRequest): ResponseEntity<PostResponse>
    {
        return try
        {
            val boardPost = postService.insertPost(postRequest);
            ResponseEntity(PostResponse(boardPost), HttpStatus.CREATED)
        } catch (e: Exception)
        {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/boards/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun updateTutorial(@PathVariable("id") id: String, @RequestBody postRequest: PostRequest): ResponseEntity<PostResponse>
    {
        val boardPost = postService.updatePost(id, postRequest)
        return ResponseEntity.ok(PostResponse(boardPost))
    }

    @DeleteMapping("/boards/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun deleteTutorial(@PathVariable("id") id: String?): ResponseEntity<HttpStatus>
    {
        return try
        {
            boardRepository.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception)
        {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/boards/like/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun updateLike(@PathVariable("id") id: String): ResponseEntity<PostResponse>
    {
        val boardPost = postService.updateLike(id)

        return ResponseEntity.ok(boardPost)
    }
}