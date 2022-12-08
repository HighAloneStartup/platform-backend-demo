package com.platform.api.repository

open interface PostRepositoryCustom {
    open fun getCollectionName(): String

    open fun setCollectionName(collectionName: String)
}