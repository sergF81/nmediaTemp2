package ru.netology

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import ru.netology.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : CallBackPost {
            override fun liked(post: Post) {
                viewModel.like(post.id)
            }

            override fun shared(post: Post) {
                viewModel.share(post.id)
            }

            override fun removed(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun edited(post: Post) {
                viewModel.edit(post)
            }

            override fun canceled(post: Post) {
                binding.group.visibility = View.VISIBLE
                with(binding.contentTemp) {
                    setText(post.content)
                }
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
            binding.list.smoothScrollToPosition(0)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0) {
                return@observe
            }
            with(binding.content) {
                requestFocus()
                setText(post.content)
            }

        }
        binding.save.setOnClickListener {
            val content = binding.content.text.toString()
            if (content.isBlank()) {
                Toast.makeText(this, "empty content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.changeContent(content)
            viewModel.save()
            binding.content.setText("")
            binding.content.clearFocus()
            hideKeyboardFrom(this, binding.save)
            binding.group.visibility = View.INVISIBLE
        }

        binding.cancelEdit.setOnClickListener {
            binding.group.visibility = View.INVISIBLE
            binding.content.setText("")
            binding.content.clearFocus()
            hideKeyboardFrom(this, binding.cancelEdit)
        }

    }
}


class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

fun hideKeyboardFrom(context: Context, view: View?) {
    val imm =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}