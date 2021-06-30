package ru.netology

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенс...",
            published = "21 мая в 18:36",
            likesCount = 999,
            likedByMe = false,
            sharedCount = 998
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            countLike.text = transferToK(post.likesCount)
            countShared.text = transferToK(post.sharedCount)
            if (post.likedByMe) {
                R.mipmap.hardfull_foreground
            } else R.mipmap.hard
        }

        binding.like.setOnClickListener {
            post.likedByMe = !post.likedByMe
            if (post.likedByMe) {
                post.likesCount++
                binding.countLike.setText(transferToK(post.likesCount))
            } else {
                post.likesCount--
                binding.countLike.setText(transferToK(post.likesCount))
            }
            binding.like.setImageResource(
                if (post.likedByMe) {
                    R.mipmap.hardfull_foreground
                } else R.mipmap.hard
            )
        }

        binding.shared.setOnClickListener {
            post.sharedCount++
            binding.countShared.setText(transferToK(post.sharedCount))
        }

    }

}


