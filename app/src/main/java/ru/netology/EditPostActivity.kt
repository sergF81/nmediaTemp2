package ru.netology

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.databinding.ActivityEditPostBinding

class EditPostActivity : AppCompatActivity() {
    object TOTAL_COUNT {
        val TOTAL_COUNT = "total_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTemp.requestFocus()

        binding.save.setOnClickListener {

            val intent = Intent()
            if (binding.editTemp.text.isBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.editTemp.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }

            binding.cancelEdit.setOnClickListener {

            binding.contentTemp.setText("")
            binding.contentTemp.clearFocus()
          //  hideKeyboardFrom(this, binding.cancelEdit)
                finish()

        }

    }


}