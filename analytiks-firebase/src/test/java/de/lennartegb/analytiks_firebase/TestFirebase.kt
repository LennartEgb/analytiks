package de.lennartegb.analytiks_firebase

internal class TestFirebase(
    private val onLogEvent: (name: String, params: Map<String, String>) -> Unit
) : Firebase {
    override fun logEvent(name: String, params: Map<String, String>) = onLogEvent(name, params)
}
