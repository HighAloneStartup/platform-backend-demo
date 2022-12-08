package com.platform.api.repository

class PostRepositoryImpl :PostRepositoryCustom{
    private var collectionName = "posts"

    override fun getCollectionName(): String {
        return collectionName
    }

    override fun setCollectionName(collectionName: String) {
        this.collectionName = collectionName
    }
}