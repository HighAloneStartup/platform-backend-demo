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

        val imagesPath : ArrayList<String>
        if(postRequest.images !=null)
        {
            imagesPath = postRequest.images
        }
        else
        {
            imagesPath = ArrayList<String>()
        }

        var boardPost = BoardPost(
            title = postRequest.title,
            description = postRequest.description,
            published = postRequest.published,

            user = _user,

            anonymous = postRequest.anonymous,
            images = imagesPath
        )
        boardPost = boardRepository.save(boardPost)

        return boardPost;
    }

    fun updatePost(id: String, postRequest: PostRequest): BoardPost {
        val beforeBoardPost = boardRepository.findById(id).get()

        val imagesPath : ArrayList<String>
        if(postRequest.images !=null)
        {
            imagesPath = postRequest.images
        }
        else
        {
            imagesPath = ArrayList<String>()
        }

        val afterBoardPost = BoardPost(
            title = postRequest.title,
            description = postRequest.description,
            published = postRequest.published,

            user = beforeBoardPost.user,

            anonymous = postRequest.anonymous,
            images = imagesPath
        )
        boardRepository.save(afterBoardPost)
        return afterBoardPost;
    }

    fun updateLike(id: String): BoardPost {
        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val _user: User = userRepository.findById(userDetailsImpl.id).get()

        val beforeBoardPost = boardRepository.findById(id).get()

        val newlikes = beforeBoardPost.likes

        if(newlikes.contains(_user)){
            newlikes.remove(_user)
        }
        else
        {
            newlikes.add(_user)
        }

        val afterBoardPost = BoardPost(
                title = beforeBoardPost.title,
                description = beforeBoardPost.description,
                published = beforeBoardPost.published,

                user = beforeBoardPost.user,

                anonymous = beforeBoardPost.anonymous,
                images = beforeBoardPost.images,
                likes = newlikes
        )
        boardRepository.save(afterBoardPost)
        return afterBoardPost;
    }
}