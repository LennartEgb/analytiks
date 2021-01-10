package de.lennartegb.analytiks

import android.app.Application
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Analytiks.registerService(DebugService())

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}

private class DebugService : Analytiks.Service {
    override val isEnabled: Boolean
        get() = BuildConfig.DEBUG

    override fun track(event: Analytiks.Event) {
        Timber.d("EVENT: ${event.getName()}; PARAMS: ${event.getParameters()}")
    }

    override fun track(view: Analytiks.View) {
        Timber.d("EVENT: ${view.getName()}")
    }

}