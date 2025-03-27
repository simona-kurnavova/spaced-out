package com.kurnavova.spacedout.ui.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.kurnavova.spacedout.R

/**
 * Utility class for opening URLs.
 */
object BrowserUtils {
    /**
     * Attempts to open the provided URL using `CustomTabsIntent`.
     * If the Custom Tabs cannot be launched (e.g., due to no compatible browser or an exception),
     * it falls back to opening the URL in the default web browser.
     *
     * @param context The context to use for launching the intent.
     * @param url The URL to open. It is expected to be a valid URL string.
     */
    fun openInCustomTabs(context: Context, url: String) {
        try {
            // Try to open the URL using CustomTabsIntent
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(context, url.toUri())
        } catch (_: Exception) {
            // If an exception occurs (e.g., unable to launch CustomTabsIntent), use the fallback
            openBrowser(url, context)
        }
    }

    /**
     * Opens a browser with the given URL.
     *
     * @param url The URL to open.
     * @param context The context to use.
     */
    private fun openBrowser(url: String, context: Context) {
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
