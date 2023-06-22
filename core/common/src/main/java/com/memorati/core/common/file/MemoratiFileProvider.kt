package com.memorati.core.common.file

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class MemoratiFileProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun intentProvider(
        file: File,
        title: String,
    ): IntentProvider = object : IntentProvider {
        override fun intent(activity: Activity): Intent =
            ShareCompat.IntentBuilder(activity)
                .setType(file.mimeType())
                .setStream(file.uri())
                .setSubject(title)
                .createChooserIntent().apply {
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
    }

    private fun File.uri(): Uri = FileProvider.getUriForFile(
        context,
        AUTHORITY,
        this,
    )

    interface IntentProvider {
        fun intent(activity: Activity): Intent
    }

    companion object {
        private const val AUTHORITY = "com.memorati.fileProvider"
    }
}

fun File.mimeType(): String = when {
    name.endsWith(".json") -> "application/json"
    else -> "application/octet-stream"
}
