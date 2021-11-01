package de.lennartegb.analytiks_firebase.impl

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import de.lennartegb.analytiks_firebase.Firebase

internal class FirebaseImpl(private val firebaseAnalytics: FirebaseAnalytics) : Firebase {
    override fun logEvent(name: String, params: Map<String, String>) {
        val paramBundle = Bundle()
        params.forEach { (key, value) -> paramBundle.putString(key, value) }
        firebaseAnalytics.logEvent(name, paramBundle)
    }
}
