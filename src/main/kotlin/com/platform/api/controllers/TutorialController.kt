package com.platform.api.controllers

import com.platform.api.repository.TutorialRepository
import com.platform.api.models.Tutorial
import com.platform.api.repository.UserRepository
import com.platform.api.security.jwt.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*
import java.util.*


@CrossOrigin(origins = ["http://localhost:9090"])
@RestController
@RequestMapping("/api")
class TutorialController(
        public val tutorialRepository: TutorialRepository
)
{
    @GetMapping("/tutorials")
    fun getAllTutorials(@RequestParam(required = false) title: String?): ResponseEntity<List<Tutorial>>
    {
        return try
        {
            var tutorials: List<Tutorial> = if (title == null) tutorialRepository.findAll() as List<Tutorial>
            else tutorialRepository.findByTitleContaining(title) as List<Tutorial>
            if (tutorials.isEmpty())
            {
                ResponseEntity(HttpStatus.NO_CONTENT)
            }
            else ResponseEntity(tutorials, HttpStatus.OK)
        } catch (e: Exception)
        {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/tutorials/{id}")
    fun getTutorialById(@PathVariable("id") id: String?): ResponseEntity<Tutorial>
    {
        val tutorialData = tutorialRepository.findById(id)
        return if (tutorialData.isPresent)
        {
            ResponseEntity(tutorialData.get(), HttpStatus.OK)
        }
        else
        {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/tutorials")
    fun createTutorial(@RequestBody tutorial: Tutorial): ResponseEntity<Tutorial>
    {
        return try
        {
            val _tutorial: Tutorial = tutorialRepository.save(Tutorial(tutorial.title, tutorial.description, false))
            ResponseEntity(_tutorial, HttpStatus.CREATED)
        } catch (e: Exception)
        {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/tutorials/{id}")
    fun updateTutorial(@PathVariable("id") id: String?, @RequestBody tutorial: Tutorial): ResponseEntity<Tutorial?>
    {
        val tutorialData = tutorialRepository.findById(id)
        return if (tutorialData.isPresent)
        {
            val _tutorial = tutorialData.get()
            _tutorial.title = tutorial.title
            _tutorial.description = tutorial.description
            _tutorial.isPublished = tutorial.isPublished
            ResponseEntity.ok(tutorialRepository.save(_tutorial))
        }
        else
        {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/tutorials/{id}")
    fun deleteTutorial(@PathVariable("id") id: String?): ResponseEntity<HttpStatus>
    {
        return try
        {
            tutorialRepository.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception)
        {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/tutorials")
    fun deleteAllTutorials(): ResponseEntity<HttpStatus>
    {
        return try
        {
            tutorialRepository.deleteAll()
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception)
        {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/tutorials/published")
    fun findByPublished(): ResponseEntity<List<Tutorial>>
    {
        return try
        {
            val tutorials = tutorialRepository.findByPublished(true) as List<Tutorial>
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