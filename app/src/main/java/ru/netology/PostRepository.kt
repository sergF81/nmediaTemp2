package ru.netology

import androidx.lifecycle.LiveData

interface PostRepository {
    fun get(): LiveData<Post>
    fun likeOnOff()
    fun like(): Int
    fun shared(): Int
}
