package com.kurnavova.spacedout.domain.api

import android.content.Context

/**
 * Manages cleanup tasks.
 */
interface CleanupManager {

    /**
     * Schedule a cleanup task.
     *
     * @param context The context to use.
     */
    fun scheduleCleanUp(context: Context)
}
