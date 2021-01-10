package de.lennartegb.analytiks

interface AnalytiksEvent {
    fun getParameters(): Map<String, String> = emptyMap()
    fun getName(): String
}
