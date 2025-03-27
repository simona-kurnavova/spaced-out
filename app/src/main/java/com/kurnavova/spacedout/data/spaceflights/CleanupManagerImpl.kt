package com.kurnavova.spacedout.data.spaceflights

import android.app.Application
import com.kurnavova.spacedout.data.spaceflights.worker.CacheCleanerWorker
import com.kurnavova.spacedout.domain.api.CleanupManager

/**
 * Implementation of [CleanupManager].
 */
class CleanupManagerImpl(
    private val app: Application
): CleanupManager {

    /**
     * Schedule a cleanup task.
     */
    override fun scheduleCleanUp() {
        CacheCleanerWorker.enqueue(app)
    }
}
