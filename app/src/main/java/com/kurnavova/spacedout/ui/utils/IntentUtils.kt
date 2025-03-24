package com.kurnavova.spacedout.ui.utils

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

/**
 * Utility class for handling/creating Intents.
 */
object IntentUtils {
    /**
     * Opens a browser with the given URL.
     *
     * @param url The URL to open.
     * @param context The context to use.
     */
    fun openBrowser(url: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}