package de.lennartegb.analytics

interface AnalyticsEvent {
    fun getParameters(): Map<String, String> = emptyMap()
    fun getName(): String
}
