package com.magicapp.android_mvi.activity.main.helper

import com.magicapp.android_mvi.models.Post
import com.magicapp.android_mvi.network.service.PostService

class MainHelperImpl(private val postService: PostService): MainHelper {
    override suspend fun allPosts(): ArrayList<Post> {
        return postService.allPosts()
    }

    override suspend fun deletePost(id: Int): Post {
        return postService.deletePost(id)
    }
}