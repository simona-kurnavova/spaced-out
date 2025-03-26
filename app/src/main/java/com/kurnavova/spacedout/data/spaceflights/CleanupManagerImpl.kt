package com.kurnavova.spacedout.data.spaceflights

import android.content.Context
import com.kurnavova.spacedout.data.spaceflights.worker.CacheCleanerWorker
import com.kurnavova.spacedout.domain.api.CleanupManager

/**
 * Implementation of [CleanupManager].
 */
object CleanupManagerImpl: CleanupManager {

    /**
     * Schedule a cleanup task.
     */
    override fun scheduleCleanUp(context: Context) {
        CacheCleanerWorker.enqueue(context)
    }
}
