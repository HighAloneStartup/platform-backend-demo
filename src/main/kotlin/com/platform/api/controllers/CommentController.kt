package com.platform.api.controllers

import com.platform.api.models.Comment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/postcomment")
class CommentController
{
    @Autowired
    private val commentService: CommentService? = null

    @PostMapping("/insertcomment")
    fun insertComment(@RequestBody postedComment: CommentPostRequestEntity): ResponseEntity<ResponseObjectService>
    {
        val inputComment: Comment = postedComment.getCommentEntity()
        val inputPostId: IdObjectEntity  = postedComment.getPostId()
        return ResponseEntity<ResponseObjectService>(commentService.insertComment(inputComment, inputPostId.getId()), HttpStatus.OK)
    }

    @PostMapping("/getcomments")
    fun getComments(@RequestBody inputPostId: IdObjectEntity): ResponseEntity<ResponseObjectService>
    {
        return ResponseEntity<ResponseObjectService>(commentService.getComments(inputPostId.getId()), HttpStatus.OK)
    }
}
