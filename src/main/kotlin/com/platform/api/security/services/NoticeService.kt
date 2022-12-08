package com.platform.api.security.services

import com.platform.api.models.NoticePost
import com.platform.api.models.User
import com.platform.api.payload.request.NoticeRequest
import com.platform.api.payload.response.NoticeResponse
import com.platform.api.repository.NoticeRepository
import com.platform.api.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.stereotype.Service

@Service
class NoticeService(
        val noticeRepository: NoticeRepository,
        val userRepository: UserRepository
)
{
    fun getPost(id: String?): NoticeResponse {
        val post = noticeRepository.findById(id).get()

        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val _user: User = userRepository.findById(userDetailsImpl.id).get()
        val _user_uid = _user.uid

        val newlikes = post.likes
        val liked = newlikes.contains(_user_uid)

        return NoticeResponse(post, liked)
    }


    fun insertPost(noticeRequest: NoticeRequest): NoticePost {

        var noticePost = NoticePost(
            title = noticeRequest.title,
            description = noticeRequest.description,
            published = noticeRequest.published,
            fileLink = noticeRequest.fileLink,
            fileName = noticeRequest.fileName
        )

        noticePost = noticeRepository.save(noticePost)

        return noticePost;
    }


    fun updateLike(id: String): NoticeResponse {
        val userDetailsImpl = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl;
        val _user: User = userRepository.findById(userDetailsImpl.id).get()
        val _user_uid = _user.uid

        val beforeNoticePost = noticeRepository.findById(id).get()

        val newlikes = beforeNoticePost.likes
        val liked = newlikes.contains(_user_uid)
        if(liked){
            newlikes.remove(_user_uid)
        }
        else
        {
            newlikes.add(_user_uid)
        }

        val afterNoticePost = NoticePost(
                id = beforeNoticePost.id,
                title = beforeNoticePost.title,
                description = beforeNoticePost.description,
                published = beforeNoticePost.published,
                fileLink = beforeNoticePost.fileLink,
                fileName = beforeNoticePost.fileName,
                createdAt = beforeNoticePost.createdAt,
                images = beforeNoticePost.images,
                likes = newlikes
        )
        noticeRepository.save(afterNoticePost)


        return NoticeResponse(afterNoticePost, !liked);
    }
}