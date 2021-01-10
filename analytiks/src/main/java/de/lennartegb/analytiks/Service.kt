package de.lennartegb.analytiks

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
    fun track(event: AnalyticsEvent)

    /**
     * Tracks a view of a user. E.g. the user opens a specific screen, or a simple view will be
     * shown.
     */
    fun track(view: AnalyticsView)

}