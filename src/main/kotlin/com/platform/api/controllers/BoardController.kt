package com.platform.api.controllers

import com.platform.api.repository.BoardRepository
import com.platform.api.models.BoardPost
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["http://localhost:9090"])
@RestController
@RequestMapping("/api")
open class BoardController(
        val boardRepository: BoardRepository
)
{
    @GetMapping("/boards")
    fun getAllTutorials(@RequestParam(required = false) title: String?): ResponseEntity<List<BoardPost>>
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
    fun getTutorialById(@PathVariable("id") id: String?): ResponseEntity<BoardPost>
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
    fun createTutorial(@RequestBody boardPost: BoardPost): ResponseEntity<BoardPost>
    {
        return try
        {
            val _boardPost: BoardPost = boardRepository.save(BoardPost(boardPost.title, boardPost.description, false))
            ResponseEntity(_boardPost, HttpStatus.CREATED)
        } catch (e: Exception)
        {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/boards/{id}")
    fun updateTutorial(@PathVariable("id") id: String?, @RequestBody boardPost: BoardPost): ResponseEntity<BoardPost?>
    {
        val tutorialData = boardRepository.findById(id)
        return if (tutorialData.isPresent)
        {
            val _tutorial = tutorialData.get()
            _tutorial.title = boardPost.title
            _tutorial.description = boardPost.description
            _tutorial.isPublished = boardPost.isPublished
            ResponseEntity.ok(boardRepository.save(_tutorial))
        }
        else
        {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/boards/{id}")
    fun deleteTutorial(@PathVariable("id") id: String?): ResponseEntity<HttpStatus>
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
    fun deleteAllTutorials(): ResponseEntity<HttpStatus>
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
    fun findByPublished(): ResponseEntity<List<BoardPost>>
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