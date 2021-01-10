package de.lennartegb.analytiks

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail


internal class AnalytiksTest {


    @BeforeEach
    fun cleanUpAnalytics() {
        Analytiks.unregisterAllServices()
    }


    @Nested
    inner class GetServiceCount {
        @Test
        fun `when no service was added returns 0`() {
            assertEquals(expected = 0, actual = Analytiks.serviceCount)
        }
    }

    @Nested
    inner class RegisterService {

        @Test
        fun `with one service and serviceCount returns 1`() {
            Analytiks.registerService(object : Analytiks.Service {
                override val isEnabled: Boolean
                    get() = fail("Should never be called")

                override fun track(event: Analytiks.Event) {
                    fail("Should never be called")
                }

                override fun track(view: Analytiks.View) {
                    fail("Should never be called")
                }

            })

            assertEquals(expected = 1, actual = Analytiks.serviceCount)
        }

        @Test
        fun `with two services and serviceCount returns 2`() {
            val testService = object :Analytiks.Service {
                override val isEnabled: Boolean
                    get() = fail("Should never be called")

                override fun track(event: Analytiks.Event) {
                    fail("Should never be called")
                }

                override fun track(view: Analytiks.View) {
                    fail("Should never be called")
                }

            }

            Analytiks.registerService(testService)
            Analytiks.registerService(testService)

            assertEquals(expected = 2, actual = Analytiks.serviceCount)
        }

    }

    @Nested
    inner class TrackEvent {

        private val testEvent = object : Analytiks.Event {
            override fun getName(): String = "TEST_EVENT"
        }

        private fun getTestService(isEnabled: Boolean, onTrack: () -> Unit) =
            object :Analytiks.Service {
                override val isEnabled: Boolean = isEnabled

                override fun track(event: Analytiks.Event) {
                    onTrack()
                }

                override fun track(view: Analytiks.View) {
                    fail("View tracking should never be called when tracking event!")
                }

            }

        @Test
        fun `if service is enabled calls event tracking of child services`() {
            var trackingIsCalled = false
            Analytiks.registerService(
                getTestService(
                    isEnabled = true,
                    onTrack = { trackingIsCalled = true })
            )
            Analytiks.track(testEvent)
            assertTrue(
                actual = trackingIsCalled,
                message = "Tracking should have been called and changed the test boolean"
            )
        }

        @Test
        fun `if service is not enabled does not call event tracking of child services`() {
            var trackingIsCalled = false
            Analytiks.registerService(
                getTestService(
                    isEnabled = false,
                    onTrack = { trackingIsCalled = true })
            )
            Analytiks.track(testEvent)
            assertFalse(
                actual = trackingIsCalled,
                message = "Tracking should have not been called and not changed the test boolean"
            )
        }
    }

    @Nested
    inner class TrackView {

        private val testView = object : Analytiks.View {
            override fun getName(): String = "TEST_VIEW"
        }
        private fun getTestService(isEnabled: Boolean, onTrack: () -> Unit) =
            object :Analytiks.Service {
                override val isEnabled: Boolean = isEnabled

                override fun track(event: Analytiks.Event) {
                    fail("Event tracking should never be called when tracking view!")
                }

                override fun track(view: Analytiks.View) {
                    onTrack()
                }

            }

        @Test
        fun `if service is enabled calls view tracking of child services`() {
            var trackingIsCalled = false
            Analytiks.registerService(
                getTestService(
                    isEnabled = true,
                    onTrack = { trackingIsCalled = true })
            )
            Analytiks.track(testView)
            assertTrue(
                actual = trackingIsCalled,
                message = "Tracking should have been called and changed the test boolean"
            )
        }

        @Test
        fun `if service is not enabled does not call view tracking of child services`() {
            var trackingIsCalled = false
            Analytiks.registerService(
                getTestService(
                    isEnabled = false,
                    onTrack = { trackingIsCalled = true })
            )
            Analytiks.track(testView)
            assertFalse(
                actual = trackingIsCalled,
                message = "Tracking should have not been called and not changed the test boolean"
            )
        }

    }
}