package com.platform.api.security.services

import com.platform.api.models.BoardPost
import com.platform.api.models.Comment
import com.platform.api.models.User
import com.platform.api.payload.request.PostRequest
import com.platform.api.payload.response.PostResponse
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
    fun getPost(id: String?): PostResponse{
        val post = boardRepository.findById(id).get()

        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val _user: User = userRepository.findById(userDetailsImpl.id).get()
        val _user_uid = _user.uid

        val newlikes = post.likes
        val liked = newlikes.contains(_user_uid)

        return PostResponse(post, liked)
    }


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
            id =beforeBoardPost.id,
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

    fun updateLike(id: String): PostResponse {
        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val _user: User = userRepository.findById(userDetailsImpl.id).get()
        val _user_uid = _user.uid

        val beforeBoardPost = boardRepository.findById(id).get()

        val newlikes = beforeBoardPost.likes
        val liked = newlikes.contains(_user_uid)
        if(liked){
            newlikes.remove(_user_uid)
        }
        else
        {
            newlikes.add(_user_uid)
        }

        val afterBoardPost = BoardPost(
                id = beforeBoardPost.id,
                title = beforeBoardPost.title,
                description = beforeBoardPost.description,
                published = beforeBoardPost.published,

                user = beforeBoardPost.user,

                anonymous = beforeBoardPost.anonymous,
                images = beforeBoardPost.images,
                likes = newlikes
        )
        boardRepository.save(afterBoardPost)


        return PostResponse(afterBoardPost, !liked);
    }
}