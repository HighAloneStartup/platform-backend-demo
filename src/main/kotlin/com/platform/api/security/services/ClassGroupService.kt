package com.platform.api.security.services

import com.platform.api.models.Board
import com.platform.api.models.Role
import com.platform.api.repository.BoardRepository
import com.platform.api.repository.ClassGroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClassGroupService (
        val classGroupRepository: ClassGroupRepository
){
    @Transactional
    fun getBoardByName(name: String): Board
    {
        val classgroup = classGroupRepository.findByName(name)

        return classgroup
    }

    open fun getClassGroupRolesByName(name: String) : ArrayList<Role>
    {
        val classgroup = classGroupRepository.findByName(name)

        return classgroup.roles
    }
}