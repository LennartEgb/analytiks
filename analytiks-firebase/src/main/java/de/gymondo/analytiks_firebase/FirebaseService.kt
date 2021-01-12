package de.gymondo.analytiks_firebase

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import de.gymondo.analytiks_firebase.extensions.toBundle
import de.lennartegb.analytiks.AnalytiksAction
import de.lennartegb.analytiks.AnalytiksService

class FirebaseService(context: Context) : AnalytiksService {

    private val firebase: FirebaseAnalytics = FirebaseAnalytics.getInstance(context).apply {
        this.setAnalyticsCollectionEnabled(true)
        this.setSessionTimeoutDuration(3000)
        this.setUserId("")
    }

    override val name: String
        get() = "DEFAULT_FIREBASE_SERVICE"

    override fun track(action: AnalytiksAction) {
        when (action) {
            is AnalytiksAction.Event -> logEvent(action.name, action.params)
            is AnalytiksAction.View -> logAction(action.name, action.params)
        }
    }

    private fun logAction(name: String, params: Map<String, String>) {
        // TODO: 12.01.21 - ScreenView
    }

    private fun logEvent(name: String, params: Map<String, String>) {
        firebase.logEvent(name, params.toBundle())
    }
}
