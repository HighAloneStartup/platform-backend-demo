package com.platform.api.security.services

import com.platform.api.models.BoardPost
import com.platform.api.models.Comment
import com.platform.api.models.User
import com.platform.api.payload.request.PostRequest
import com.platform.api.repository.BoardRepository
import com.platform.api.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException

import org.springframework.stereotype.Service
import java.time.Instant
import java.util.ArrayList

@Service
class PostService(
        val boardRepository: BoardRepository,
        val userRepository: UserRepository
)
{
    fun insertPost(postRequest: PostRequest): BoardPost {
        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val _user: User = userRepository.findById(userDetailsImpl.id).get()

        val boardPost = BoardPost(
            title = postRequest.title,
            description = postRequest.description,
            published = postRequest.published,

            user = _user,

            anonymous = postRequest.anonymous
        )
        System.out.println(boardPost)
        boardRepository.save(boardPost)

        return boardPost;
    }

    fun updatePost(id: String, postRequest: PostRequest): BoardPost {
        val beforeBoardPost = boardRepository.findById(id).get()
        val afterBoardPost = BoardPost(
            title = postRequest.title,
            description = postRequest.description,
            published = postRequest.published,

            user = beforeBoardPost.user,

            anonymous = postRequest.anonymous
        )
        boardRepository.save(afterBoardPost)
        return afterBoardPost;
    }
}