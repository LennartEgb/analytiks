package de.lennartegb.analytiks

import android.app.Application
import de.lennartegb.analytiks.services.DebugService

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Analytiks.register(DebugService())
        }
    }
}
