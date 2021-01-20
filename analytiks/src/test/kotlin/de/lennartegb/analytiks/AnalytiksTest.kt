package de.lennartegb.analytiks

import de.lennartegb.analytiks.errors.AlreadyRegisteredService
import de.lennartegb.analytiks.errors.RegisteringFailed
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail


internal class AnalytiksTest {


    @BeforeEach
    fun cleanUpAnalytics() {
        Analytiks.clearAllServices()
    }


    @Test
    fun `cannot register Analytiks to itself`() {
        assertThrows<RegisteringFailed> { Analytiks.registerService(Analytiks) }
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

        private inner class TestService() : AnalytiksService {
            override fun track(action: AnalytiksAction) {
                fail("Should never be called")
            }
        }

        @Test
        fun `with one service and serviceCount returns 1`() {
            Analytiks.registerService(TestService())
            assertEquals(expected = 1, actual = Analytiks.serviceCount)
        }

        @Test
        fun `with two different service instances and serviceCount returns 2`() {
            Analytiks.registerService(TestService())
            Analytiks.registerService(TestService())
            assertEquals(expected = 2, actual = Analytiks.serviceCount)
        }

        @Test
        fun `with same service instances throws AlreadyRegistered`() {
            val service = TestService()
            assertThrows<AlreadyRegisteredService> {
                Analytiks.registerService(service)
                Analytiks.registerService(service)
            }
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

            Analytiks.registerService(service)

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

            Analytiks.registerService(service)

            Analytiks.track(testView)

            assertTrue(
                actual = trackingIsCalled,
                message = "Tracking should have been called and changed the test boolean"
            )
        }
    }
}
