package de.lennartegb.analytiks_firebase

import de.lennartegb.analytiks.AnalytiksAction
import org.junit.Test

internal class FirebaseServiceTest {

    @Test
    fun trackView() {
        var trackedParams: Map<String, String>? = null
        var trackedName: String? = null

        val service = FirebaseService(
            TestFirebase(
                onLogEvent = { name, params ->
                    trackedName = name
                    trackedParams = params
                }
            )
        )

        service.track(AnalytiksAction.View(name = "ScreenName"))

        assert(trackedName == "screen_view")
        assert(trackedParams == mapOf("screen_name" to "ScreenName"))
    }

    @Test
    fun trackEventWithoutParams() {
        var trackedParams: Map<String, String>? = null
        var trackedName: String? = null

        val service = FirebaseService(
            TestFirebase(
                onLogEvent = { name, params ->
                    trackedName = name
                    trackedParams = params
                }
            )
        )

        service.track(AnalytiksAction.Event(name = "click"))

        assert(trackedName == "click")
        assert(trackedParams == emptyMap<String, String>())
    }

    @Test
    fun trackEventWithParams() {
        var trackedParams: Map<String, String>? = null
        var trackedName: String? = null

        val service = FirebaseService(
            TestFirebase(
                onLogEvent = { name, params ->
                    trackedName = name
                    trackedParams = params
                }
            )
        )

        service.track(AnalytiksAction.Event(name = "click", params = mapOf("position" to "today")))

        assert(trackedName == "click")
        assert(trackedParams == mapOf("position" to "today"))
    }
}
