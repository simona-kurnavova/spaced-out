package com.kurnavova.spacedout.ui.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.net.toUri
import com.kurnavova.spacedout.R

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

        // Check if there is an activity that can handle the intent
        val activity = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
            .firstOrNull()

        if (activity != null) {
            // If there are activities that can handle the intent, start the activity
            context.startActivity(intent)
        } else {
            // If no activity can handle the intent, show a Toast
            Toast.makeText(context,
                context.getString(R.string.no_browser_available_toast), Toast.LENGTH_SHORT).show()
        }
    }
}
