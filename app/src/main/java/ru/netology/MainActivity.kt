package ru.netology

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                countLike.text = post.likesCount.toString()
                countShared.text = post.sharedCount.toString()
                like.setImageResource(
                    if (post.likedByMe) R.mipmap.hardfull_foreground else R.drawable.hard
                )
            }
        }
        binding.like.setOnClickListener {
            viewModel.likeOnOff()
            binding.countLike.text = transferToK(viewModel.like())
        }

        binding.shared.setOnClickListener {
            binding.countShared.text = transferToK(viewModel.shared())
        }
    }
}


