package com.platform.api.security.services

import com.platform.api.models.Comment
import com.platform.api.models.User
import com.platform.api.payload.request.CommentRequest
import com.platform.api.repository.CommentRepository
import com.platform.api.repository.PostRepository
import com.platform.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CommentService(
        @Autowired
        private val userRepository: UserRepository,
        @Autowired
        private val commentRepository: CommentRepository
)
{
    fun postComment(commentRequest: CommentRequest) : Comment
    {
        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val user: User = userRepository.findById(userDetailsImpl.id).get()

        val comment : Comment = Comment(
                user = user,
                anonymous = commentRequest.anonymous,
                description = commentRequest.description
        )

        return commentRepository.save(comment)
    }

    fun getComment(description: String) : Comment
    {
        return commentRepository.findByDescription(description)
    }
}