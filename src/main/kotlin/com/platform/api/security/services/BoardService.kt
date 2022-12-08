package com.platform.api.security.services

import com.platform.api.models.Board
import com.platform.api.models.Role
import com.platform.api.repository.BoardRepository
import com.platform.api.repository.NoticeRepository
import com.platform.api.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws

@Service
class BoardService (
        val boardRepository: BoardRepository,
){
    @Transactional
    fun getBoardByName(name: String): Board
    {
        val board = boardRepository.findByName(name)

        return board
    }

    open fun getBoardRolesByName(name: String) : ArrayList<Role>
    {
        val board = boardRepository.findByName(name)

        return board.roles
    }
}