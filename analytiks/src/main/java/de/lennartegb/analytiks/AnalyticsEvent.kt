package de.lennartegb.analytiks

interface AnalyticsEvent {
    fun getParameters(): Map<String, String> = emptyMap()
    fun getName(): String
}
