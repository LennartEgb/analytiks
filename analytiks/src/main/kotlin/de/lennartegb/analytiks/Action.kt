package de.lennartegb.analytiks

/**
 * An action that must be send to a service.
 */
sealed interface Action {

    /**
     * An event that represents an action of the user.
     * @param name of the given event
     * @param params to send to the [AnalytiksService]
     */
    data class Event(
        val name: String,
        val params: Params = emptyMap()
    ) : Action {
        constructor(
            name: String,
            params: (MutableParams.() -> Unit)
        ) : this(name = name, params = mutableMapOf<String, String>().apply(params))
    }

    /**
     * An event that represents a view of the user.
     * @param name of the screen to be tracked.
     */
    class View(val name: String) : Action
}

