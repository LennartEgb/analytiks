package de.lennartegb.analytiks

/**
 * Analytics object, that is used for tracking [Action]s. The pattern
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
object Analytiks : Service {
    private val services = mutableSetOf<Service>()

    /**
     * Registers an [Service] to be used for tracking.
     * @param service An [Service] to be registered.
     */
    fun register(service: Service) {
        synchronized(services) {
            require(service !is Analytiks) { "Cannot register Analytiks to itself." }
            services.add(service)
        }
    }
    
    override fun track(action: Action) {
        services.forEach { it.track(action) }
    }

}
