package com.kurnavova.spacedout.domain.api

/**
 * Manages cleanup tasks.
 */
interface CleanupManager {

    /**
     * Schedule a cleanup task.
     */
    fun scheduleCleanUp()
}
