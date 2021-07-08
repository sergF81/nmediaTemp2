package ru.netology

import androidx.lifecycle.ViewModel

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like(id: Int) = repository.like(id)
    fun share(id: Int) = repository.share(id)
}
