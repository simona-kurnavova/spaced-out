package com.kurnavova.spacedout.data.spaceflights.worker

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.kurnavova.spacedout.data.spaceflights.SpaceFlightRepositoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

/**
 * Worker that periodically cleans article cache.
 */
class CacheCleanerWorker(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams), KoinComponent {

    private val repository: SpaceFlightRepositoryImpl by inject()

    override suspend fun doWork(): Result {
        Log.d(TAG, "Cleaning cache")

        val success = repository.cleanOldCache()

        return if (success) {
            Result.success()
        } else {
            Result.failure()
        }
    }

    companion object {
        private const val WORK_NAME = "CacheCleanerWorker"
        private const val PERIOD_IN_DAYS = 7L

        private const val TAG = "CacheCleanerWorker"

        /**
         * Enqueues the worker.
         *
         * @param context The context to use.
         */
        fun enqueue(context: Context) {
            Log.d(TAG, "Enqueuing worker")

            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()

            val request = PeriodicWorkRequestBuilder<CacheCleanerWorker>(PERIOD_IN_DAYS, TimeUnit.DAYS)
                .setConstraints(constraints)
                .setInitialDelay(PERIOD_IN_DAYS, TimeUnit.DAYS)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }

        /**
         * Cancels the worker.
         *
         * @param context The context to use.
         */
        fun cancel(context: Context) {
            Log.d(TAG, "Cancelling worker")
            WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        }
    }
}
