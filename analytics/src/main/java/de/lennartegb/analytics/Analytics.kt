package de.lennartegb.analytics


/**
 * Analytics object, that is used for tracking [AnalyticsEvent]s and [AnalyticsView]s. The pattern
 * of Jake Whartons Timber was used as a role model. Example usage:
 * ```
 * // Analytics service with Firebase usage under the hood
 * class FirebaseService: AnalyticsService { ... }
 *
 * // Adds the service to the analytics.
 * Analytics.addService(FirebaseService())
 *
 * // Creates an event or view that must be tracked
 * class CustomClickEvent: AnalyticsEvent { ... }
 *
 * Analytics.track(CustomClickEvent())
 * ```
 */
object Analytics {

    private val services = mutableListOf<AnalyticsService>()

    private val MAIN_SERVICE = object : AnalyticsService {
        override val isEnabled: Boolean = true

        override fun track(event: AnalyticsEvent) {
            services.filter { it.isEnabled }
                .forEach { it.track(event) }
        }

        override fun track(view: AnalyticsView) {
            services.filter { it.isEnabled }
                .forEach { it.track(view) }
        }

    }

    /**
     * Return amount of services registered.
     */
    val serviceCount: Int
        get() = synchronized(services) {
            return@synchronized services.size
        }

    fun registerService(service: AnalyticsService) {
        synchronized(services) {
            services.add(service)
        }
    }

    fun unregisterAllServices() {
        services.clear()
    }

    // region Tracking
    fun track(event: AnalyticsEvent) {
        MAIN_SERVICE.track(event)
    }

    fun track(view: AnalyticsView) {
        MAIN_SERVICE.track(view)
    }
    // endregion

}