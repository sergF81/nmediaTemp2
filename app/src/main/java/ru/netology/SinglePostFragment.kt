package ru.netology

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.databinding.FragmentSinglePostBinding


class SinglePostFragment : Fragment() {

    val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSinglePostBinding.inflate(
            inflater,
            container,
            false
        )
        println(arguments?.getInt("MyId"))


        val post = viewModel.data.value
            ?.find { it.id == arguments?.getInt("MyId")
                }
        binding.apply{
            like.setOnClickListener {
                like.text = post?.likesCount?.let { it -> transferToK(it) }
                if (post != null) {
                    viewModel.like(post.id)

                }

            }

            shared.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    if (post != null) {
                        putExtra(Intent.EXTRA_TEXT, post.content)
                    }
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
                if (post != null) {
                    viewModel.share(post.id)
                }
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove_post -> {
                                findNavController().navigate(R.id.action_singlePostFragment_to_feedFragment)
                                true
                            }
                            R.id.edit_post -> {
                                val bundle = Bundle()
                                bundle.putString("MyArg", post?.content)
                                post?.let { it1 -> viewModel.edit(it1) }
                                findNavController().navigate(
                                    R.id.action_singlePostFragment_to_editPostFragment,
                                    bundle
                                )
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }

                viewModel.data.observe(viewLifecycleOwner) {
            val post = viewModel.data.value
                ?.find { it.id == arguments?.getInt("MyId")
                    }


            binding.apply {
                author.text = post?.author.toString()
                published.text = post?.published
                contents.text = post?.content
                like.text = post?.let { transferToK(it?.likesCount) }
                shared.text = post?.let { transferToK(it?.sharedCount) }
                like.isChecked = post?.likedByMe == true

                if (post?.video == null) play.visibility = View.INVISIBLE
                else View.VISIBLE

            }
        }
        return binding.root
    }
}

