package de.lennartegb.analytiks

import de.lennartegb.analytiks.errors.AlreadyRegisteredService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail


internal class AnalytiksTest {


    @BeforeEach
    fun cleanUpAnalytics() {
        Analytiks.clearAllServices()
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

        private inner class TestService(override val name: String) : Analytiks.Service {
            override val isEnabled: Boolean
                get() = fail("Should never be called")

            override fun track(action: Analytiks.Action) {
                fail("Should never be called")
            }
        }

        @Test
        fun `with one service and serviceCount returns 1`() {
            Analytiks.registerService(TestService(name = "TEST_1"))
            assertEquals(expected = 1, actual = Analytiks.serviceCount)
        }

        @Test
        fun `with two different services and serviceCount returns 2`() {
            Analytiks.registerService(TestService(name = "TEST_1"))
            Analytiks.registerService(TestService(name = "TEST_2"))
            assertEquals(expected = 2, actual = Analytiks.serviceCount)
        }

        @Test
        fun `with two equal services throws IllegalArgumentException`() {
            Analytiks.registerService(TestService(name = "TEST_1"))

            assertThrows<AlreadyRegisteredService> {
                Analytiks.registerService(TestService(name = "TEST_1"))
            }
        }

    }

    @Nested
    inner class TrackEvent {

        private val testEvent = Analytiks.Action.Event("TEST_EVENT")

        private inner class TestService(
            override val name: String = "TEST_SERVICE",
            override val isEnabled: Boolean,
            private val onTrack: () -> Unit,
        ) : Analytiks.Service {
            override fun track(action: Analytiks.Action) {
                when (action) {
                    is Analytiks.Action.Event -> onTrack()
                    is Analytiks.Action.View -> fail("View tracking should never be called when tracking event!")
                }
            }
        }

        @Test
        fun `if service is enabled calls event tracking of child services`() {
            var trackingIsCalled = false
            val service = TestService(isEnabled = true, onTrack = { trackingIsCalled = true })

            Analytiks.registerService(service)

            Analytiks.track(testEvent)

            assertTrue(
                actual = trackingIsCalled,
                message = "Tracking should have been called and changed the test boolean"
            )
        }

        @Test
        fun `if service is not enabled does not call event tracking of child services`() {
            var trackingIsCalled = false
            val service = TestService(isEnabled = false, onTrack = { trackingIsCalled = true })

            Analytiks.registerService(service)

            Analytiks.track(testEvent)

            assertFalse(
                actual = trackingIsCalled,
                message = "Tracking should have not been called and not changed the test boolean"
            )
        }

    }

    @Nested
    inner class TrackView {

        private val testView = Analytiks.Action.View(name = "TEST_VIEW")

        private inner class TestService(
            override val name: String = "TEST_SERVICE",
            override val isEnabled: Boolean,
            private val onTrack: () -> Unit
        ) : Analytiks.Service {
            override fun track(action: Analytiks.Action) {
                when (action) {
                    is Analytiks.Action.Event -> fail("Event tracking should never be called when tracking view!")
                    is Analytiks.Action.View -> onTrack()
                }
            }
        }

        @Test
        fun `if service is enabled calls view tracking of child services`() {
            var trackingIsCalled = false
            val service = TestService(isEnabled = true, onTrack = { trackingIsCalled = true })

            Analytiks.registerService(service)

            Analytiks.track(testView)

            assertTrue(
                actual = trackingIsCalled,
                message = "Tracking should have been called and changed the test boolean"
            )
        }

        @Test
        fun `if service is not enabled does not call view tracking of child services`() {
            var trackingIsCalled = false
            val service = TestService(isEnabled = false, onTrack = { trackingIsCalled = true })

            Analytiks.registerService(service)

            Analytiks.track(testView)

            assertFalse(
                actual = trackingIsCalled,
                message = "Tracking should have not been called and not changed the test boolean"
            )
        }
    }
}
