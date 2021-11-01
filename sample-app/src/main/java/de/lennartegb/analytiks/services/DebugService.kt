package de.lennartegb.analytiks.services

import android.util.Log
import de.lennartegb.analytiks.AnalytiksAction
import de.lennartegb.analytiks.AnalytiksService

class DebugService(private val tag: String = "Analytiks") : AnalytiksService {
    override fun track(action: AnalytiksAction) {
        val type = when (action) {
            is AnalytiksAction.Event -> "EVENT"
            is AnalytiksAction.View  -> "VIEW"
        }
        val params = action.params.takeUnless { it.isEmpty() }
        val message = listOfNotNull(
            "$type:",
            action.name,
            params
        ).joinToString(" ")
        Log.d(tag, message)
    }
}
