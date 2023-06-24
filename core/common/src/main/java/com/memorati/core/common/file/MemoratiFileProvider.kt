package com.memorati.core.common.file

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.memorati.core.common.di.AppId
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class MemoratiFileProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    @AppId private val appId: String,
) {

    fun intentProvider(
        file: File,
        title: String,
    ): IntentProvider = object : IntentProvider {
        override fun intent(context: Context): Intent =
            ShareCompat.IntentBuilder(context)
                .setType(file.mimeType())
                .setStream(file.uri())
                .setSubject(title)
                .createChooserIntent().apply {
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
    }

    private fun File.uri(): Uri = FileProvider.getUriForFile(
        context,
        "$appId.fileProvider",
        this,
    )

    interface IntentProvider {
        fun intent(context: Context): Intent
    }
}

fun File.mimeType(): String = when {
    name.endsWith(".json") -> "application/json"
    else -> "application/octet-stream"
}
