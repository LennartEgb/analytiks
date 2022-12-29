package de.lennartegb.analytiks

/**
 * Service used for analytics. Examples for analytics could be Firebase, Sentry or an own
 * implementation.
 */
interface AnalytiksService {

    /**
     * Tracks an action of the user.
     * @param action [Action] to be tracked.
     */
    fun track(action: Action)

}
