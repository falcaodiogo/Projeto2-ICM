package ua.deti.pt.wearosapp.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import ua.deti.pt.wearosapp.PERMISSION
import ua.deti.pt.wearosapp.TAG
import ua.deti.pt.wearosapp.repositories.GoalsRepository
import ua.deti.pt.wearosapp.repositories.HealthServiceRepository


/**
 * Background data subscriptions are not persisted across device restarts. This receiver checks if
 * we enabled background data and, if so, registers again.
 */
class StartupReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val repository = GoalsRepository(context)

        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        runBlocking {
            if (repository.passiveGoalsEnabled.first()) {
                // Make sure that we have permission
                val result = context.checkSelfPermission(PERMISSION)

                if (result == PackageManager.PERMISSION_GRANTED) {
                    scheduleWorker(context)
                } else {
                    // We may have lost the permission somehow. Mark that background data is
                    // disabled so the state is consistent the next time the user opens the app UI.
                    repository.setPassiveGoalsEnabled(false)
                }
            }
        }
    }

    /**
     * BroadcastReceiver's onReceive must complete within 10 seconds. During device startup,
     * sometimes the call to register for background data takes longer than that and our
     * BroadcastReceiver gets destroyed before it completes. Instead we schedule a WorkManager
     * job to perform the registration.
     *
     * @property context the application context used to access the WorkManager instance.
     */
    private fun scheduleWorker(context: Context) {
        Log.i(TAG, "Enqueuing worker")
        WorkManager.getInstance(context).enqueue(
            OneTimeWorkRequestBuilder<RegisterForBackgroundDataWorker>().build()
        )
    }
}

/**
 * A CoroutineWorker subclass that performs the actual task of subscribing for goals in the background.
 * This worker is scheduled by the StartupReceiver and runs in the background to ensure that
 * the subscription process does not interfere with the user experience during device startup.
 */
class RegisterForBackgroundDataWorker(
    private val appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        Log.i(TAG, "Worker Running")
        val healthServiceRepository = HealthServiceRepository(appContext)
        healthServiceRepository.subscribeForGoals()
        return Result.success()
    }
}

