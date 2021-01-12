package de.lennartegb.analytiks

/**
 * An action that must be send to a service.
 * @param name of the given action
 * @param params to send for analytics
 */
@Suppress("MemberVisibilityCanBePrivate")
sealed class AnalytiksAction(val name: String, val params: Map<String, String>) {

    /**
     * An event that represents an action of the user.
     */
    class Event(name: String, params: Map<String, String> = emptyMap()) :
        AnalytiksAction(name, params)

    /**
     * An event that represents a view of the user.
     */
    class View(name: String, params: Map<String, String> = emptyMap()) :
        AnalytiksAction(name, params)
}
