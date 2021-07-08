package ru.netology

import androidx.lifecycle.LiveData

interface PostRepository {
    fun get(): LiveData<List<Post>>
    fun like(id: Int)
    fun share(id: Int)
}
