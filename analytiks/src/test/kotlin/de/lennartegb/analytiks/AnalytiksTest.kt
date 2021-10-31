package de.lennartegb.analytiks

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue
import kotlin.test.fail

internal class AnalytiksTest {

    @BeforeEach
    fun cleanUpAnalytics() {
        Analytiks.clearAllServices()
    }

    @Test
    fun `cannot register Analytiks to itself`() {
        assertThrows<IllegalStateException> { Analytiks.register(Analytiks) }
    }

    @Nested
    inner class RegisterService {

        private inner class TestService() : AnalytiksService {
            override fun track(action: AnalytiksAction) {
                fail("Should never be called")
            }
        }

        @Test
        fun `with one service and serviceCount returns 1`() {
            Analytiks.register(TestService())
        }

        @Test
        fun `with two different service instances and serviceCount returns 2`() {
            Analytiks.register(TestService())
            Analytiks.register(TestService())
        }

        @Test
        fun `with two same instances registered serviceCount returns 1`() {
            val service = TestService()

            Analytiks.register(service)
            Analytiks.register(service)
        }
    }

    @Nested
    inner class TrackEvent {

        private val testEvent = AnalytiksAction.Event("TEST_EVENT")

        private inner class TestService(
            private val onTrack: () -> Unit,
        ) : AnalytiksService {
            override fun track(action: AnalytiksAction) {
                when (action) {
                    is AnalytiksAction.Event -> onTrack()
                    is AnalytiksAction.View -> fail("View tracking should never be called when tracking event!")
                }
            }
        }

        @Test
        fun `with registered service changes boolean flag to true`() {
            var trackingIsCalled = false
            val service = TestService(onTrack = { trackingIsCalled = true })

            Analytiks.register(service)

            Analytiks.track(testEvent)

            assertTrue(
                actual = trackingIsCalled,
                message = "Tracking should have been called and changed the test boolean"
            )
        }
    }

    @Nested
    inner class TrackView {

        private val testView = AnalytiksAction.View(name = "TEST_VIEW")

        private inner class TestService(
            private val onTrack: () -> Unit
        ) : AnalytiksService {
            override fun track(action: AnalytiksAction) {
                when (action) {
                    is AnalytiksAction.Event -> fail("Event tracking should never be called when tracking view!")
                    is AnalytiksAction.View -> onTrack()
                }
            }
        }

        @Test
        fun `with registered service changes boolean flag to true`() {
            var trackingIsCalled = false
            val service = TestService(onTrack = { trackingIsCalled = true })

            Analytiks.register(service)

            Analytiks.track(testView)

            assertTrue(
                actual = trackingIsCalled,
                message = "Tracking should have been called and changed the test boolean"
            )
        }
    }
}
