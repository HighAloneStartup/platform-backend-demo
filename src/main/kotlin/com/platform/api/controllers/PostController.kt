package com.platform.api.controllers

import com.platform.api.repository.PostRepository
import com.platform.api.models.Post
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api")
open class PostController(
        val postRepository: PostRepository
)
{
    @GetMapping("/posts")
    fun getAllTutorials(@RequestParam(required = false) title: String?): ResponseEntity<List<Post>>
    {
        return try
        {
            var boardPosts: List<Post> = if (title == null) postRepository.findAll() as List<Post>
            else postRepository.findByTitleContaining(title) as List<Post>
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

    @GetMapping("/posts/{id}")
    fun getTutorialById(@PathVariable("id") id: String?): ResponseEntity<Post>
    {
        val tutorialData = postRepository.findById(id)
        return if (tutorialData.isPresent)
        {
            ResponseEntity(tutorialData.get(), HttpStatus.OK)
        }
        else
        {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/posts")
    fun createTutorial(@RequestBody boardPost: Post): ResponseEntity<Post>
    {
        return try
        {
            val _boardPost: Post = postRepository.save(Post(boardPost.title, boardPost.description, false))
            ResponseEntity(_boardPost, HttpStatus.CREATED)
        } catch (e: Exception)
        {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/posts/{id}")
    fun updateTutorial(@PathVariable("id") id: String?, @RequestBody boardPost: Post): ResponseEntity<Post?>
    {
        val tutorialData = postRepository.findById(id)
        return if (tutorialData.isPresent)
        {
            val _tutorial = tutorialData.get()
            _tutorial.title = boardPost.title
            _tutorial.description = boardPost.description
            _tutorial.isPublished = boardPost.isPublished
            ResponseEntity.ok(postRepository.save(_tutorial))
        }
        else
        {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/posts/{id}")
    fun deleteTutorial(@PathVariable("id") id: String?): ResponseEntity<HttpStatus>
    {
        return try
        {
            postRepository.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception)
        {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/posts")
    fun deleteAllTutorials(): ResponseEntity<HttpStatus>
    {
        return try
        {
            postRepository.deleteAll()
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception)
        {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/posts/published")
    fun findByPublished(): ResponseEntity<List<Post>>
    {
        return try
        {
            val tutorials = postRepository.findByPublished(true) as List<Post>
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