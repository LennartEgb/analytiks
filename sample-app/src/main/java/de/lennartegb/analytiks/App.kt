package de.lennartegb.analytiks

import android.app.Application
import android.util.Log

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Analytiks.register(DebugService())
        }
    }

}

private class DebugService(private val tag: String = "Analytiks") : AnalytiksService {
    override fun track(action: AnalytiksAction) {
        val type = when (action) {
            is AnalytiksAction.Event -> "EVENT"
            is AnalytiksAction.View -> "VIEW"
        }
        val name = action.name
        val params = action.params
        Log.d(tag, "$type: $name $params")
    }
}
