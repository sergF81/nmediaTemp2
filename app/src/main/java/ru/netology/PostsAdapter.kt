package ru.netology

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.databinding.CardPostBinding

interface CallBackPost {
    fun liked(post: Post)
    fun shared(post: Post)
    fun removed(post: Post)
    fun edited(post: Post)
    fun canceled(post: Post)
    fun video(post: Post)
}

class PostsAdapter(

    private val callBackPost: CallBackPost
) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, callBackPost)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val callBackPost: CallBackPost
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {

        binding.apply {
            author.text = post.author
            published.text = post.published
            contents.text = post.content
            like.text = transferToK(post.likesCount)
            shared.text = transferToK(post.sharedCount)
            like.isChecked = post.likedByMe

            if (post.video == null) play.visibility = View.INVISIBLE
            else View.VISIBLE


            like.setOnClickListener {
                callBackPost.liked(post)
                like.text = transferToK(post.likesCount)
            }

            shared.setOnClickListener {
                callBackPost.shared(post)
                shared.text = transferToK(post.sharedCount)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove_post -> {
                                callBackPost.removed(post)
                                true
                            }
                            R.id.edit_post -> {
                                callBackPost.edited(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
            play.setOnClickListener {
                callBackPost.video(post)
            }
        }
    }
}