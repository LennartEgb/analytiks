package de.lennartegb.analytiks_android_log

import android.util.Log
import de.lennartegb.analytiks.Action
import de.lennartegb.analytiks.Service

class DebugService(private val tag: String = "Analytiks") : Service {

    override fun track(action: Action) {
        Log.d(tag, action.message)
    }

    private val Action.message: String get() = when (this) {
        is Action.Event -> listOfNotNull(type, name, params.takeUnless { it.isEmpty() })
        is Action.View -> listOfNotNull(type, name)
    }.joinToString(separator = " ")

    private val Action.type: String get() = when(this) {
        is Action.Event -> "$tag Event:"
        is Action.View -> "$tag View:"
    }
}
