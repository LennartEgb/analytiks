package de.lennartegb.analytiks

import de.lennartegb.analytiks.errors.AlreadyRegisteredService


/**
 * Analytics object, that is used for tracking [AnalytiksAction]s. The pattern
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
object Analytiks : AnalytiksService {
    private const val MAIN_SERVICE_NAME = "ANALYTIKS_MAIN_SERVICE"
    private val services = mutableListOf<AnalytiksService>()

    /**
     * Return amount of services registered.
     */
    val serviceCount: Int
        get() = synchronized(services) {
            return@synchronized services.size
        }

    /**
     * Registers an [AnalytiksService] to be used for tracking.
     * @throws AlreadyRegisteredService if two services with the same name are registered.
     */
    fun registerService(service: AnalytiksService) {
        synchronized(services) {
            service.takeUnless { it.name in services.map(AnalytiksService::name) }
                ?.also { services.add(it) }
                ?: throw AlreadyRegisteredService("The service ${service.name} is already registered.")
        }
    }

    /**
     * Clears all [AnalytiksService]s.
     */
    fun clearAllServices() {
        services.clear()
    }

    override val name: String
        get() = MAIN_SERVICE_NAME

    override fun track(action: AnalytiksAction) {
        services.forEach { it.track(action) }
    }

}