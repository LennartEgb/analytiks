package de.lennartegb.analytiks

import de.lennartegb.analytiks.errors.RegisteringFailed


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
 * val CustomClickEvent = AnalytiksAction.Event(name = "CUSTOM")
 *
 * Analytiks.track(CustomClickEvent)
 * ```
 */
object Analytiks : AnalytiksService {
    private val services = mutableSetOf<AnalytiksService>()

    /**
     * Return amount of services registered.
     */
    val serviceCount: Int
        get() = synchronized(services) {
            return@synchronized services.size
        }

    /**
     * Registers an [AnalytiksService] to be used for tracking.
     * @param service An [AnalytiksService] to be registered.
     */
    fun registerService(service: AnalytiksService) {
        synchronized(services) {
            if (service is Analytiks) throw RegisteringFailed("Cannot register Analytiks to itself.")
            services.add(service)
        }
    }

    /**
     * Clears all [AnalytiksService]s.
     */
    fun clearAllServices() {
        services.clear()
    }

    override fun track(action: AnalytiksAction) {
        services.forEach { it.track(action) }
    }

}
