package de.lennartegb.analytiks

import de.lennartegb.analytiks.errors.AlreadyRegisteredService


/**
 * Analytics object, that is used for tracking [Analytiks.Action]s. The pattern
 * of Jake Whartons Timber was used as a role model. The idea behind this is a SOLID implementation
 * for using analytics based on the implementation by sofakingforever but in a lightweight
 * interface. Example usage:
 * ```
 * // Analytics service with Firebase usage under the hood
 * class FirebaseService: AnalyticsService { ... }
 *
 * // Adds the service to the analytics.
 * Analytiks.registerService(FirebaseService())
 *
 * // Creates an event or view that must be tracked
 * val CustomClickEvent = Analytiks.Action.Event(name = "CUSTOM")
 *
 * Analytiks.track(CustomClickEvent)
 * ```
 */
object Analytiks {

    /**
     * An action that must be send to a service.
     * @param name of the given action
     * @param params to send for analytics
     */
    @Suppress("MemberVisibilityCanBePrivate")
    sealed class Action(val name: String, val params: Map<String, String>) {
        /**
         * An event that represents an action of the user.
         */
        class Event(name: String, params: Map<String, String> = emptyMap()) : Action(name, params)

        /**
         * An event that represents a view of the user.
         */
        class View(name: String, params: Map<String, String> = emptyMap()) : Action(name, params)
    }

    /**
     * Service used for analytics. Examples for analytics could be Firebase, Sentry or an own
     * implementation.
     */
    interface Service {

        /**
         * The name of the [Analytiks.Service]
         */
        val name: String

        /**
         * If the [Analytiks.Service] is enabled. This can be done for example by a configuration
         * file.
         */
        val isEnabled: Boolean

        /**
         * Tracks an action of the user.
         */
        fun track(action: Action)
    }


    private val services = mutableListOf<Service>()

    /**
     * Return amount of services registered.
     */
    val serviceCount: Int
        get() = synchronized(services) {
            return@synchronized services.size
        }

    /**
     * Registers an [Analytiks.Service] to be used for tracking.
     * @throws AlreadyRegisteredService if two services with the same name are registered.
     */
    fun registerService(service: Service) {
        synchronized(services) {
            service.takeUnless { it.name in services.map(Service::name) }
                ?.also { services.add(it) }
                ?: throw AlreadyRegisteredService("The service ${service.name} is already registered.")
        }
    }

    /**
     * Clears all [Analytiks.Service]s.
     */
    fun clearAllServices() {
        services.clear()
    }

    /**
     * Tracks an [Analytiks.Action] and dispatches it to all registered services.
     */
    fun track(action: Action) {
        services.filter { it.isEnabled }
            .forEach { it.track(action) }
    }

}