package ru.netology

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likesCount: Int,
    var likedByMe: Boolean = false,
    var sharedCount: Int
)
