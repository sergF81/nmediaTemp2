package ru.netology

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.databinding.FragmentEditPostBinding


class EditPostFragment : Fragment() {

    val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditPostBinding.inflate(
            inflater,
            container,
            false
        )

        val text1 = arguments?.getString("MyArg")

        binding.contentTemp.text = text1
        binding.editTemp.requestFocus()
        binding.editTemp.setText(text1)
        binding.save.setOnClickListener {

            viewModel.changeContent(binding.editTemp.text.toString())
            viewModel.save()
            findNavController().navigateUp()
        }

        binding.cancelEdit.setOnClickListener {

            binding.contentTemp.setText("")
            binding.contentTemp.clearFocus()
            findNavController().navigateUp()
        }
        return binding.root

    }


}