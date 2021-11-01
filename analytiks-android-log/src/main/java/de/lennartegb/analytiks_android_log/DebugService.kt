package de.lennartegb.analytiks_android_log

import android.util.Log
import de.lennartegb.analytiks.AnalytiksAction
import de.lennartegb.analytiks.AnalytiksService

class DebugService(private val tag: String = "Analytiks") : AnalytiksService {
    override fun track(action: AnalytiksAction) {
        Log.d(tag, action.message)
    }

    private val AnalytiksAction.message: String get() {
        return listOfNotNull(
            "${this.tag}:",
            name,
            params.takeUnless { it.isEmpty() }
        ).joinToString(separator = " ")
    }

    private val AnalytiksAction.tag: String get() = when(this) {
        is AnalytiksAction.Event -> "Event"
        is AnalytiksAction.View -> "View"
    }
}
