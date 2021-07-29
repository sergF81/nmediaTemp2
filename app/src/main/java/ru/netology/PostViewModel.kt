package ru.netology

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

val empty = Post(
id  = 0,
author = "",
content = "",
published = "",
likesCount = 0,
likedByMe = false,
sharedCount = 0
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFileImpl(application)
    val data = repository.get()
    val edited = MutableLiveData(empty)
    fun like(id: Int) = repository.like(id)

    fun share(id: Int) = repository.share(id)
    fun removeById(id: Int) = repository.removeById(id)
    fun video(id: Int) = repository.video(id)
    fun singlePost(id: Int) = repository.singlePost(id)
    fun save() {
       edited.value?.let {
           repository.save(it)
       }
        edited.value = empty
    }

    fun edit(post: Post){
        edited.value = post
    }

    fun changeContent(content: String){
        edited.value?.let {
            val newPost = content.trim()
            if (newPost != it.content){
                edited.value = edited.value?.copy(content = content)
            }
        }
    }
}
