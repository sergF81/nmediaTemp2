package ru.netology

import androidx.lifecycle.LiveData

interface PostRepository {
    fun get(): LiveData<List<Post>>
    fun likeOnOff(id: Int)
    fun like(id: Int)
    fun share(id: Int)
}
