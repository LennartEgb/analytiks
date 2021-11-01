package de.lennartegb.analytiks

/**
 * An action that must be send to a service.
 * @param name of the given action
 * @param params to send for analytics
 */
@Suppress("MemberVisibilityCanBePrivate")
sealed class AnalytiksAction(val name: String, val params: Params) {

    /**
     * An event that represents an action of the user.
     */
    class Event(
        name: String,
        params: Params = emptyMap()
    ) : AnalytiksAction(name, params) {
        constructor(
            name: String,
            params: (MutableParams.() -> Unit)
        ) : this(name = name, params = mutableMapOf<String, String>().apply(params))

        override fun equals(other: Any?): Boolean {
            if (other !is Event) return false
            return name == other.name && params == other.params
        }

        override fun hashCode(): Int = javaClass.hashCode()
    }

    /**
     * An event that represents a view of the user.
     */
    class View(
        name: String,
        params: Params = emptyMap()
    ) : AnalytiksAction(name, params) {
        constructor(
            name: String,
            params: (MutableParams.() -> Unit)
        ) : this(name = name, params = mutableMapOf<String, String>().apply(params))

        override fun equals(other: Any?): Boolean {
            if (other !is View) return false
            return name == other.name && params == other.params
        }

        override fun hashCode(): Int = javaClass.hashCode()
    }
}

