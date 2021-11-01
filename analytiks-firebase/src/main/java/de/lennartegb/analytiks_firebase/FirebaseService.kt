package de.lennartegb.analytiks_firebase

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import de.lennartegb.analytiks.AnalytiksAction
import de.lennartegb.analytiks.AnalytiksService
import de.lennartegb.analytiks_firebase.impl.FirebaseImpl

/**
 * This is how an [AnalytiksService] for firebase logging could look like.
 */
@Suppress("unused")
class FirebaseService internal constructor(private val firebase: Firebase) : AnalytiksService {

    constructor(context: Context) : this(FirebaseImpl(FirebaseAnalytics.getInstance(context)))

    override fun track(action: AnalytiksAction) {
        when (action) {
            is AnalytiksAction.Event -> logEvent(action)
            is AnalytiksAction.View -> logScreenView(action)
        }
    }

    private fun logEvent(event: AnalytiksAction.Event) {
        firebase.logEvent(event.name, event.params)
    }

    private fun logScreenView(view: AnalytiksAction.View) {
        val screenName = FirebaseAnalytics.Param.SCREEN_NAME to view.name
        firebase.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, view.params + screenName)
    }
}
