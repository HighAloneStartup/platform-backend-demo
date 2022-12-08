package com.platform.api.security.services

import com.platform.api.models.BoardPost
import com.platform.api.models.Comment
import com.platform.api.models.User
import com.platform.api.payload.request.PostRequest
import com.platform.api.payload.response.PostResponse
import com.platform.api.repository.PostRepository
import com.platform.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.stereotype.Service
import java.time.Instant
import java.util.ArrayList

@Service
class PostService(
        @Autowired
        private val postRepository: PostRepository,
        @Autowired
        private val userRepository: UserRepository
)
{
    fun getPost(id: String?): PostResponse{
        val post = postRepository.findById(id).get()

        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val _user: User = userRepository.findById(userDetailsImpl.id).get()
        val _user_uid = _user.uid

        val newlikes = post.likes
        val liked = newlikes.contains(_user_uid)

        return PostResponse(post, _user_uid)
    }


    fun insertPost(collectionName:String, postRequest: PostRequest): BoardPost {
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

        postRepository.setCollectionName(collectionName)
        boardPost = postRepository.save(boardPost)

        return boardPost;
    }

    fun updatePost(name: String, id: String, postRequest: PostRequest): BoardPost {
        val beforeBoardPost = postRepository.findById(id).get()

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
                createdAt = beforeBoardPost.createdAt,
                likes = beforeBoardPost.likes,
                images = imagesPath,
                comments = beforeBoardPost.comments
        )

        postRepository.setCollectionName(name)
        postRepository.save(afterBoardPost)
        return afterBoardPost;
    }

    fun updateLike(name: String, id: String): PostResponse {
        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val _user: User = userRepository.findById(userDetailsImpl.id).get()
        val _user_uid = _user.uid

        val beforeBoardPost = postRepository.findById(id).get()

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
                createdAt = beforeBoardPost.createdAt,
                likes = newlikes,
                images = beforeBoardPost.images,
                comments = beforeBoardPost.comments
        )
        postRepository.setCollectionName(name)
        postRepository.save(afterBoardPost)


        return PostResponse(afterBoardPost, _user_uid);
    }



    fun updateComment(name:String, id: String, comment: Comment): PostResponse {
        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val _user: User = userRepository.findById(userDetailsImpl.id).get()
        val _user_uid = _user.uid

        val beforeBoardPost = postRepository.findById(id).get()

        var comments = beforeBoardPost.comments
        comments.add(comment)

        val afterBoardPost = BoardPost(
                id = beforeBoardPost.id,
                title = beforeBoardPost.title,
                description = beforeBoardPost.description,
                published = beforeBoardPost.published,

                user = beforeBoardPost.user,

                anonymous = beforeBoardPost.anonymous,
                createdAt = beforeBoardPost.createdAt,
                likes = beforeBoardPost.likes,
                images = beforeBoardPost.images,
                comments = comments
        )
        postRepository.setCollectionName(name)
        postRepository.save(afterBoardPost)

        return PostResponse(afterBoardPost, _user_uid);
    }
}