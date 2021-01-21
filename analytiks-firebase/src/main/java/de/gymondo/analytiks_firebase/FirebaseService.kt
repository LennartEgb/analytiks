package de.gymondo.analytiks_firebase

import com.google.firebase.analytics.FirebaseAnalytics
import de.gymondo.analytiks_firebase.extensions.toBundle
import de.lennartegb.analytiks.AnalytiksAction
import de.lennartegb.analytiks.AnalytiksService

class FirebaseService(private val firebase: FirebaseAnalytics) : AnalytiksService {

    override val name: String
        get() = "DEFAULT_FIREBASE_SERVICE"

    override fun track(action: AnalytiksAction) {
        when (action) {
            is AnalytiksAction.Event -> logEvent(action.name, action.params)
            is AnalytiksAction.View -> logView(action.name, action.params)
        }
    }

    private fun logView(name: String, params: Map<String, String>) {
        val eventBundle = mapOf(FirebaseAnalytics.Param.SCREEN_NAME to name)
            .plus(params)
            .toBundle()
        firebase.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, eventBundle)
    }

    private fun logEvent(name: String, params: Map<String, String>) {
        val eventBundle = mapOf(FirebaseAnalytics.Param.ITEM_NAME to name)
            .plus(params)
            .toBundle()
        firebase.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, eventBundle)
    }
}
