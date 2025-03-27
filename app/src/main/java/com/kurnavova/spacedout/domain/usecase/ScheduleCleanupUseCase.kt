package com.kurnavova.spacedout.domain.usecase

import com.kurnavova.spacedout.domain.api.CleanupManager

/**
 * Use case for scheduling cleanup tasks.
 */
class ScheduleCleanupUseCase(
    private val cleanupManager: CleanupManager
){
    /**
     * Schedules a cleanup task.
     */
    operator fun invoke() {
        cleanupManager.scheduleCleanUp()
    }
}
