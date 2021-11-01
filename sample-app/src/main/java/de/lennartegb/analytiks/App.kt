package de.lennartegb.analytiks

import android.app.Application
import de.lennartegb.analytiks_android_log.DebugService

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Analytiks.register(DebugService())
        }
    }
}
