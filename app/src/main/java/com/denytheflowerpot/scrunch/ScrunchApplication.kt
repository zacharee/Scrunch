package com.denytheflowerpot.scrunch

import android.app.Application
import android.content.Intent
import com.denytheflowerpot.scrunch.managers.NotificationManager
import com.denytheflowerpot.scrunch.managers.SettingsManager
import com.denytheflowerpot.scrunch.managers.SoundPlaybackManager
import com.denytheflowerpot.scrunch.services.FoldActionSignalingService
import com.denytheflowerpot.scrunch.util.PermissionUtils
import org.lsposed.hiddenapibypass.HiddenApiBypass

class ScrunchApplication: Application() {
    val settingsManager: SettingsManager by lazy {
        SettingsManager(applicationContext)
    }
    val notificationManager: NotificationManager by lazy {
        NotificationManager(applicationContext)
    }
    val soundPlaybackManager: SoundPlaybackManager by lazy { SoundPlaybackManager(applicationContext) }

    override fun onCreate() {
        super.onCreate()

        HiddenApiBypass.addHiddenApiExemptions("")

        instance = this
        soundPlaybackManager.loadPreviousSounds()
        updateServiceState()
    }

    fun updateServiceState() {
        if (settingsManager.serviceStarted && !PermissionUtils.needsToGrantReadLogs(this)) {
            startForegroundService(getServiceIntent())
        } else {
            stopService(getServiceIntent())
        }
    }

    fun getServiceIntent(): Intent {
        return Intent(this, FoldActionSignalingService::class.java)
    }

    companion object {
        lateinit var instance: ScrunchApplication
            private set
    }
}