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

private class DebugService : Service {
    override val isEnabled: Boolean
        get() = BuildConfig.DEBUG

    override fun track(event: AnalyticsEvent) {
        Timber.d("EVENT: ${event.getName()}; PARAMS: ${event.getParameters()}")
    }

    override fun track(view: AnalyticsView) {
        Timber.d("EVENT: ${view.getName()}")
    }

}