package ru.netology

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostRepositoryFileImpl(private val context: Context) : PostRepository {


    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val gson = Gson()
    private val fileName = "posts.json"
    private var nextId = 1
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(fileName)
        if (file.exists()) {
            context.openFileInput(fileName).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {
            sync()
        }
    }

    private fun sync() {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    override fun get(): LiveData<List<Post>> = data

    override fun like(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(likedByMe = !it.likedByMe)
        }
        posts = posts.map { it ->
            when {
                it.id != id -> it
                it.likedByMe -> {
                    it.copy(likesCount = it.likesCount + 1)
                        .also {
                            transferToK(it.likesCount)
                        }
                }
                else -> it.copy(likesCount = it.likesCount - 1)
                    .also {
                        transferToK(it.likesCount)
                    }
            }
        }
        data.value = posts

        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0) {
            posts = listOf(
                post.copy(
                    id = nextId++, author = "Me", likedByMe = false, published = "Now", likesCount = 0, sharedCount = 0
                )
            ) + posts
        } else {
            posts = posts.map{if (it.id != post.id) it else it.copy(content = post.content)}
        }
        data.value = posts
        sync()
    }

    override fun share(id: Int) {
        posts = posts.map {
            if (it.id != id) it else {
                it.copy(sharedCount = it.sharedCount + 1)
                    .also {
                        transferToK(it.sharedCount)
                        println(transferToK(it.sharedCount))
                    }
            }
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Int) {

        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun video(id: Int) {
    }

    override fun singlePost(id: Int) {

    }
}