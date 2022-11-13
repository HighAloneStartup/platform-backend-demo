package com.linhtch90.psnbackend.service

import com.platform.api.models.Post
import com.platform.api.repository.PostRepository
import com.platform.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class PostService
{
    @Autowired
    private val postRepo: PostRepository? = null

    @Autowired
    private val userRepo: UserRepository? = null
    fun insertPost(inputPost: PostEntity): ResponseObjectService
    {
        val responseObj = ResponseObjectService()
        inputPost.setCreatedAt(Instant.now())
        responseObj.setStatus("success")
        responseObj.setMessage("success")
        responseObj.setPayload(postRepo!!.save(inputPost))
        return responseObj
    }

    fun findPostByUserId(inputUserId: IdObjectEntity): ResponseObjectService
    {
        val responseObj = ResponseObjectService()
        val userPostsOpt: Optional<List<PostEntity>> = postRepo.findByUserIdOrderByCreatedAtDesc(inputUserId.getId())
        return if (userPostsOpt.isEmpty())
        {
            responseObj.setStatus("fail")
            responseObj.setMessage("cannot find any post from user id: " + inputUserId.getId())
            responseObj.setPayload(null)
            responseObj
        }
        else
        {
            val userPosts: List<PostEntity> = userPostsOpt.get()
            responseObj.setStatus("success")
            responseObj.setMessage("success")
            responseObj.setPayload(userPosts)
            responseObj
        }
    }

    fun findPostByFollowing(inputUserId: IdObjectEntity): ResponseObjectService
    {
        val responseObj = ResponseObjectService()
        val optUser: Optional<UserEntity?> = userRepo!!.findById(inputUserId.getId())
        return if (optUser.isEmpty())
        {
            responseObj.setStatus("fail")
            responseObj.setMessage("cannot find any post from user id: " + inputUserId.getId())
            responseObj.setPayload(null)
            responseObj
        }
        else
        {
            val user: UserEntity = optUser.get()
            if (user.getFollowing() != null)
            {
                // if user followed someone, get their ids
                val followingIds: MutableList<String> = ArrayList()
                for (id in user.getFollowing())
                {
                    followingIds.add(id)
                }
                // based on these ids, get their equivalent posts
                val listPosts: MutableList<PostByFollowing> = ArrayList<PostByFollowing>()
                for (followingId in followingIds)
                {
                    // get following user info based on Id
                    var followingUser = UserEntity()
                    val optFollowingUser: Optional<UserEntity?> = userRepo.findById(followingId)
                    if (optFollowingUser.isPresent())
                    {
                        followingUser = optFollowingUser.get()
                    }
                    followingUser.setPassword("")

                    // get equivalent posts
                    val followingPostsOpt: Optional<List<PostEntity>> = postRepo.findByUserId(followingId)
                    if (followingPostsOpt.isPresent)
                    {
                        // if followed account has any post, collect them
                        val followingPosts: List<PostEntity> = followingPostsOpt.get()
                        if (followingPosts != null)
                        {
                            for (item in followingPosts)
                            {
                                listPosts.add(PostByFollowing(followingUser, item))
                            }
                        }
                    }
                }
                Collections.sort(listPosts, Comparator<T> { o1: T, o2: T -> o2.getPost().getCreatedAt().compareTo(o1.getPost().getCreatedAt()) })
                responseObj.setStatus("success")
                responseObj.setMessage("success")
                responseObj.setPayload(listPosts)
                responseObj
            }
            else
            {
                responseObj.setStatus("fail")
                responseObj.setMessage("user id: " + inputUserId.getId().toString() + " has empty following list")
                responseObj.setPayload(null)
                responseObj
            }
        }
    }

    fun updatePostByComment(inputPost: PostEntity): ResponseObjectService
    {
        val responseObj = ResponseObjectService()
        val optPost: Optional<PostEntity?> = postRepo!!.findById(inputPost.getId())
        return if (optPost.isEmpty())
        {
            responseObj.setStatus("fail")
            responseObj.setMessage("cannot find post id: " + inputPost.getId())
            responseObj.setPayload(null)
            responseObj
        }
        else
        {
            // inputPost.setCreatedAt(Instant.now());
            postRepo.save<Post>(inputPost)
            responseObj.setStatus("success")
            responseObj.setMessage("post is updated successfully")
            responseObj.setPayload(inputPost)
            responseObj
        }
    }

    fun updatePostByLove(doubleId: DoubleIdObjectEntity): ResponseObjectService
    {
        // id 1 - post Id, id 2 - user who liked post
        val responseObj = ResponseObjectService()
        val optPost: Optional<PostEntity?> = postRepo!!.findById(doubleId.getId1())
        return if (optPost.isEmpty())
        {
            responseObj.setStatus("fail")
            responseObj.setMessage("cannot find post id: " + doubleId.getId1())
            responseObj.setPayload(null)
            responseObj
        }
        else
        {
            val targetPost: PostEntity = optPost.get()
            var loveList: MutableList<String?> = targetPost.getLove()
            if (loveList == null)
            {
                loveList = ArrayList()
            }
            // love and unlove a post
            if (!loveList.contains(doubleId.getId2()))
            {
                loveList.add(doubleId.getId2())
            }
            else
            {
                loveList.remove(doubleId.getId2())
            }
            targetPost.setLove(loveList)
            postRepo.save<Post>(targetPost)
            responseObj.setStatus("success")
            responseObj.setMessage("update love to the target post id: " + targetPost.getId())
            responseObj.setPayload(targetPost)
            responseObj
        }
    }

    fun updatePostByShare(doubleId: DoubleIdObjectEntity): ResponseObjectService
    {
        // id 1 - post Id, id 2 - user who shared post
        val responseObj = ResponseObjectService()
        val optPost: Optional<PostEntity?> = postRepo!!.findById(doubleId.getId1())
        return if (optPost.isEmpty())
        {
            responseObj.setStatus("fail")
            responseObj.setMessage("cannot find post id: " + doubleId.getId1())
            responseObj.setPayload(null)
            responseObj
        }
        else
        {
            val targetPost: PostEntity = optPost.get()
            var shareList: MutableList<String?> = targetPost.getShare()
            if (shareList == null)
            {
                shareList = ArrayList()
            }
            // save id of user who shared the post then update post
            shareList.add(doubleId.getId2())
            targetPost.setShare(shareList)
            postRepo.save<Post>(targetPost)
            // update post list of user who shared the post
            targetPost.setUserId(doubleId.getId2())
            targetPost.setId(null)
            targetPost.setContent("Shared a post: " + targetPost.getContent())
            targetPost.setLove(ArrayList<E>())
            targetPost.setShare(ArrayList<E>())
            targetPost.setComment(ArrayList<E>())
            postRepo.save<Post>(targetPost)
            responseObj.setStatus("success")
            responseObj.setMessage("add a share to the target post id: " + targetPost.getId())
            responseObj.setPayload(targetPost)
            responseObj
        }
    }
}