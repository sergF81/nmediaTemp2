package ru.netology

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likesCount: Int,
    val likedByMe: Boolean,
    val sharedCount: Int
)
