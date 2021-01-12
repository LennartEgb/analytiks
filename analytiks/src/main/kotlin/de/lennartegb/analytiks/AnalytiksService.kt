package de.lennartegb.analytiks

/**
 * Service used for analytics. Examples for analytics could be Firebase, Sentry or an own
 * implementation.
 */
interface AnalytiksService {

    /**
     * The name of the [AnalytiksService]
     */
    val name: String

    /**
     * Tracks an action of the user.
     */
    fun track(action: AnalytiksAction)
}