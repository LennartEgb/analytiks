package de.lennartegb.analytiks


/**
 * Analytics object, that is used for tracking [AnalytiksEvent]s and [AnalytiksView]s. The pattern
 * of Jake Whartons Timber was used as a role model. Example usage:
 * ```
 * // Analytics service with Firebase usage under the hood
 * class FirebaseService: AnalyticsService { ... }
 *
 * // Adds the service to the analytics.
 * Analytiks.addService(FirebaseService())
 *
 * // Creates an event or view that must be tracked
 * class CustomClickEvent: AnalyticsEvent { ... }
 *
 * Analytiks.track(CustomClickEvent())
 * ```
 */
object Analytiks {


    interface Event {
        fun getParameters(): Map<String, String> = emptyMap()
        fun getName(): String
    }


    interface View {
        fun getName(): String
    }


    /**
     * Service used for analytics. Examples for analytics could be Firebase, Sentry or an own
     * implementation.
     */
    interface Service {

        /**
         * If the service is enabled. This could be controlled by a configuration file, e.g.
         */
        val isEnabled: Boolean

        /**
         * Tracks an event of the user.
         */
        fun track(event: Event)

        /**
         * Tracks a view of a user. E.g. the user opens a specific screen, or a simple view will be
         * shown.
         */
        fun track(view: View)
    }


    private val services = mutableListOf<Service>()

    private val MAIN_SERVICE = object : Service {
        override val isEnabled: Boolean = true

        override fun track(event: Event) {
            services.filter { it.isEnabled }
                .forEach { it.track(event) }
        }

        override fun track(view: View) {
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

    fun registerService(service: Service) {
        synchronized(services) {
            services.add(service)
        }
    }

    fun unregisterAllServices() {
        services.clear()
    }

    // region Tracking
    fun track(event: Event) {
        MAIN_SERVICE.track(event)
    }

    fun track(view: View) {
        MAIN_SERVICE.track(view)
    }
    // endregion

}