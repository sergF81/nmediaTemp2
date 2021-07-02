package ru.netology

import androidx.lifecycle.ViewModel

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun likeOnOff() = repository.likeOnOff()
    fun like(): Int = repository.like()
    fun shared(): Int = repository.shared()

}
