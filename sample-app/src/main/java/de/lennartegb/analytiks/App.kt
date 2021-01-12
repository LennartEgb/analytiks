package de.lennartegb.analytiks

import android.app.Application
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Analytiks.registerService(DebugService())
        }

    }

}

private class DebugService : AnalytiksService {
    override val name: String
        get() = "DEBUG_SERVICE"

    override fun track(action: AnalytiksAction) {
        when (action) {
            is AnalytiksAction.Event -> Timber.d("EVENT: $action")
            is AnalytiksAction.View -> Timber.d("VIEW: $action")
        }
    }

}