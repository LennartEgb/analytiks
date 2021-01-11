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

private class DebugService : Analytiks.Service {
    override val name: String
        get() = "DEBUG_SERVICE"

    override fun track(action: Analytiks.Action) {
        when (action) {
            is Analytiks.Action.Event -> Timber.d("EVENT: $action")
            is Analytiks.Action.View -> Timber.d("VIEW: $action")
        }
    }

}