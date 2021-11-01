package de.lennartegb.analytiks

import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * This is how an [AnalytiksService] for firebase logging could look like.
 */
@Suppress("unused")
class FirebaseService(context: Context) : AnalytiksService {
    private val firebase = FirebaseAnalytics.getInstance(context)

    override fun track(action: AnalytiksAction) {
        when (action) {
            is AnalytiksAction.Event -> logEvent(action)
            is AnalytiksAction.View -> logScreenView(action)
        }
    }

    private fun logEvent(event: AnalytiksAction.Event) {
        val params = Bundle()
        event.params.takeUnless { it.isEmpty() }?.forEach { (key, value) ->
            params.putString(key, value)
        }
        firebase.logEvent(event.name, params)
    }

    private fun logScreenView(view: AnalytiksAction.View) {
        val screenBundle = bundleOf(FirebaseAnalytics.Param.SCREEN_NAME to view.name)
        view.params.takeUnless { it.isEmpty() }?.forEach { (key, value) ->
            screenBundle.putString(key, value)
        }
        firebase.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screenBundle)
    }
}
