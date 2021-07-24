package ru.netology

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class MainActivityContract: ActivityResultContract<Unit, String?>() {
    override fun createIntent(context: Context, input: Unit?): Intent =
        Intent(context, MainActivity::class.java)

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }
}
