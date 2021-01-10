package de.lennartegb.analytics

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail


internal class AnalyticsTest {


    @BeforeEach
    fun cleanUpAnalytics() {
        Analytics.unregisterAllServices()
    }


    @Nested
    inner class GetServiceCount {
        @Test
        fun `when no service was added returns 0`() {
            assertEquals(expected = 0, actual = Analytics.serviceCount)
        }
    }

    @Nested
    inner class RegisterService {

        @Test
        fun `with one service and serviceCount returns 1`() {
            Analytics.registerService(object : AnalyticsService {
                override val isEnabled: Boolean
                    get() = fail("Should never be called")

                override fun track(event: AnalyticsEvent) {
                    fail("Should never be called")
                }

                override fun track(view: AnalyticsView) {
                    fail("Should never be called")
                }

            })

            assertEquals(expected = 1, actual = Analytics.serviceCount)
        }

        @Test
        fun `with two services and serviceCount returns 2`() {
            val testService = object : AnalyticsService {
                override val isEnabled: Boolean
                    get() = fail("Should never be called")

                override fun track(event: AnalyticsEvent) {
                    fail("Should never be called")
                }

                override fun track(view: AnalyticsView) {
                    fail("Should never be called")
                }

            }

            Analytics.registerService(testService)
            Analytics.registerService(testService)

            assertEquals(expected = 2, actual = Analytics.serviceCount)
        }

    }

    @Nested
    inner class TrackEvent {

        private val testEvent = object : AnalyticsEvent {
            override fun getName(): String = "TEST_EVENT"
        }

        private fun getTestService(isEnabled: Boolean, onTrack: () -> Unit) =
            object : AnalyticsService {
                override val isEnabled: Boolean = isEnabled

                override fun track(event: AnalyticsEvent) {
                    onTrack()
                }

                override fun track(view: AnalyticsView) {
                    fail("View tracking should never be called when tracking event!")
                }

            }

        @Test
        fun `if service is enabled calls event tracking of child services`() {
            var trackingIsCalled = false
            Analytics.registerService(
                getTestService(
                    isEnabled = true,
                    onTrack = { trackingIsCalled = true })
            )
            Analytics.track(testEvent)
            assertTrue(
                actual = trackingIsCalled,
                message = "Tracking should have been called and changed the test boolean"
            )
        }

        @Test
        fun `if service is not enabled does not call event tracking of child services`() {
            var trackingIsCalled = false
            Analytics.registerService(
                getTestService(
                    isEnabled = false,
                    onTrack = { trackingIsCalled = true })
            )
            Analytics.track(testEvent)
            assertFalse(
                actual = trackingIsCalled,
                message = "Tracking should have not been called and not changed the test boolean"
            )
        }
    }

    @Nested
    inner class TrackView {

        private val testView = object : AnalyticsView {
            override fun getName(): String = "TEST_VIEW"
        }
        private fun getTestService(isEnabled: Boolean, onTrack: () -> Unit) =
            object : AnalyticsService {
                override val isEnabled: Boolean = isEnabled

                override fun track(event: AnalyticsEvent) {
                    fail("Event tracking should never be called when tracking view!")
                }

                override fun track(view: AnalyticsView) {
                    onTrack()
                }

            }

        @Test
        fun `if service is enabled calls view tracking of child services`() {
            var trackingIsCalled = false
            Analytics.registerService(
                getTestService(
                    isEnabled = true,
                    onTrack = { trackingIsCalled = true })
            )
            Analytics.track(testView)
            assertTrue(
                actual = trackingIsCalled,
                message = "Tracking should have been called and changed the test boolean"
            )
        }

        @Test
        fun `if service is not enabled does not call view tracking of child services`() {
            var trackingIsCalled = false
            Analytics.registerService(
                getTestService(
                    isEnabled = false,
                    onTrack = { trackingIsCalled = true })
            )
            Analytics.track(testView)
            assertFalse(
                actual = trackingIsCalled,
                message = "Tracking should have not been called and not changed the test boolean"
            )
        }

    }
}