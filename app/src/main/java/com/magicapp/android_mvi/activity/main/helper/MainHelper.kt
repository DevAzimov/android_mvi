package com.magicapp.android_mvi.activity.main.helper

import com.magicapp.android_mvi.models.Post

interface MainHelper {
    suspend fun allPosts(): ArrayList<Post>
    suspend fun  deletePost(id: Int): Post
}