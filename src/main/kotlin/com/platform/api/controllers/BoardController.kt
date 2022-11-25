package com.platform.api.controllers

import com.platform.api.repository.BoardRepository
import com.platform.api.models.BoardPost
import com.platform.api.payload.request.PostRequest
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
    open fun getAllTutorials(@RequestParam(required = false) title: String?): ResponseEntity<List<BoardPost>>
    {
        return try
        {
            var boardPosts: List<BoardPost> = if (title == null) boardRepository.findAll() as List<BoardPost>
            else boardRepository.findByTitleContaining(title) as List<BoardPost>
            if (boardPosts.isEmpty())
            {
                ResponseEntity(HttpStatus.NO_CONTENT)
            }
            else ResponseEntity(boardPosts, HttpStatus.OK)
        } catch (e: Exception)
        {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/boards/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun getTutorialById(@PathVariable("id") id: String?): ResponseEntity<BoardPost>
    {
        val tutorialData = boardRepository.findById(id)
        return if (tutorialData.isPresent)
        {
            ResponseEntity(tutorialData.get(), HttpStatus.OK)
        }
        else
        {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/boards")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun createTutorial(@RequestBody postRequest: PostRequest): ResponseEntity<BoardPost>
    {
        return try
        {
            val _boardPost: BoardPost = postService.insertPost(postRequest);
            ResponseEntity(_boardPost, HttpStatus.CREATED)
        } catch (e: Exception)
        {
            System.out.println(e)
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/boards/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun updateTutorial(@PathVariable("id") id: String, @RequestBody postRequest: PostRequest): ResponseEntity<BoardPost?>
    {
        return ResponseEntity.ok(postService.updatePost(id, postRequest))
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

    @GetMapping("/boards/published")
    @PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('ADMIN')")
    open fun findByPublished(): ResponseEntity<List<BoardPost>>
    {
        return try
        {
            val tutorials = boardRepository.findByPublished(true) as List<BoardPost>
            if (tutorials.isEmpty())
            {
                ResponseEntity(HttpStatus.NO_CONTENT)
            }
            else ResponseEntity(tutorials, HttpStatus.OK)
        } catch (e: Exception)
        {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}