package com.magicapp.android_mvi.activity.main.intentstate

sealed class MainIntent {
    object  AllPosts : MainIntent()
    object DeletePost: MainIntent()
}