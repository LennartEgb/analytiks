package de.lennartegb.analytiks_firebase

internal interface Firebase {
    fun logEvent(name: String, params: Map<String, String>)
}
