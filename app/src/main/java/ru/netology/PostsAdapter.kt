package ru.netology

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.databinding.CardPostBinding

typealias LikeListener = (post: Post) -> Unit
typealias ShareListener = (post: Post) -> Unit

//interface CallBack{
//    fun liked(post: Post)
//    fun shared(shareCount: Int)
//}
class PostsAdapter(
    private val likeListener: LikeListener,
    private val shareListener: ShareListener
) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {
    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, likeListener, shareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    class PostViewHolder(
        private val binding: CardPostBinding,
        private val likeListener: LikeListener,
        private val shareListener: ShareListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {

            binding.apply {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                countLike.text = transferToK(post.likesCount)
                countShared.text = transferToK(post.sharedCount)
                like.setImageResource(
                    if (post.likedByMe) R.mipmap.hardfull_foreground else R.drawable.hard
                )

                like.setOnClickListener {
                    likeListener(post)
                }

                shared.setOnClickListener {
                    shareListener(post)
                }
            }
        }
    }
}