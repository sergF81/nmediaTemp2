package ru.netology

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
//import ru.netology.databinding.ActivityMainBinding
import ru.netology.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false)

        super.onCreate(savedInstanceState)
      //  val binding = ActivityMainBinding.inflate(layoutInflater)
      //  setContentView(binding.root)


        val viewModel: PostViewModel by viewModels(
            ownerProducer =::requireParentFragment
        )

        val adapter = PostsAdapter(object : CallBackPost {
            override fun liked(post: Post) {
                viewModel.like(post.id)
            }

            override fun shared(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
                viewModel.share(post.id)
            }

            override fun removed(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun singlePostById(post: Post) {
                val bundle = Bundle()
                bundle.putInt("MyId", post.id)
                viewModel.singlePost(post.id)
                findNavController().navigate(R.id.action_feedFragment_to_singlePostFragment, bundle)
            }

//            val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
//                result ?: return@registerForActivityResult
//                viewModel.changeContent(result)
//                viewModel.save()
//            }

            override fun edited(post: Post) {
                val bundle = Bundle()
                bundle.putString("MyArg", post.content)
                viewModel.edit(post)
                findNavController().navigate(R.id.action_feedFragment_to_editPostFragment, bundle)

            }

            override fun canceled(post: Post) {

            }

            override fun video(post: Post) {

                val videoIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(videoIntent)
                viewModel.video(post.id)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
            binding.list.smoothScrollToPosition(0)
        }

//        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
//            result ?: return@registerForActivityResult
//            viewModel.changeContent(result)
//            viewModel.save()
//        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
       return binding.root
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//
//    }
}


class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

//скрывание клавиатуры
fun hideKeyboardFrom(view: View) {
    val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}