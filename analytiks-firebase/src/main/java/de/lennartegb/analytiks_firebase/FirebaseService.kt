package de.lennartegb.analytiks_firebase

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import de.lennartegb.analytiks.Action
import de.lennartegb.analytiks.AnalytiksService
import de.lennartegb.analytiks_firebase.impl.FirebaseImpl
import com.google.firebase.ktx.Firebase as FirebaseOrigin

/**
 * This is how an [AnalytiksService] for firebase logging could look like.
 */
class FirebaseService internal constructor(private val firebase: Firebase) : AnalytiksService {

    @Suppress("unused")
    constructor() : this(FirebaseImpl(FirebaseOrigin.analytics))

    override fun track(action: Action) {
        when (action) {
            is Action.Event -> logEvent(action)
            is Action.View -> logScreenView(action)
        }
    }

    private fun logEvent(event: Action.Event) {
        firebase.logEvent(event.name, event.params)
    }

    private fun logScreenView(view: Action.View) {
        val screenName = mapOf(FirebaseAnalytics.Param.SCREEN_NAME to view.name)
        firebase.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screenName)
    }
}
