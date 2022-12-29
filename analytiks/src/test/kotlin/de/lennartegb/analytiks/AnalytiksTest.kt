package de.lennartegb.analytiks

import org.junit.Test
import kotlin.test.assertTrue

internal class AnalytiksTest {

    @Test(expected = IllegalArgumentException::class)
    fun registerAnalytiksThrows() {
        Analytiks.register(Analytiks)
    }

    @Test
    fun withEvent_callsEventOfTestService() {
        var isEventCalled = false
        Analytiks.register(TestService(onEvent = { isEventCalled = true }))

        Analytiks.track(Action.Event(name = "Event"))

        assertTrue(actual = isEventCalled, message = "Event must be called")
    }

    @Test
    fun withView_callsViewOfTestService() {
        var isViewCalled = false
        Analytiks.register(TestService(onView = { isViewCalled = true }))

        Analytiks.track(Action.View(name = "View"))

        assertTrue(actual = isViewCalled, message = "View must be called")
    }
}
