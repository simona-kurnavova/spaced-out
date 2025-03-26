package com.kurnavova.spacedout.domain.usecase

import android.content.Context
import com.kurnavova.spacedout.domain.api.CleanupManager

/**
 * Use case for scheduling cleanup tasks.
 */
class ScheduleCleanupUseCase(
    private val cleanupManager: CleanupManager
){
    /**
     * Schedules a cleanup task.
     *
     * @param context The context to use.
     */
    fun scheduleCleanUp(context: Context) {
        cleanupManager.scheduleCleanUp(context)
    }
}
