package de.lennartegb.analytiks

import de.lennartegb.analytiks.errors.AlreadyRegisteredService
import de.lennartegb.analytiks.errors.RegisteringFailed
import de.lennartegb.analytiks.errors.AnalytiksServiceNotFound
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertSame
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
    inner class Get {

        private inner class TestService(private val onTrack: () -> Unit) : AnalytiksService {
            override val name: String
                get() = "TEST_SERVICE"

            override fun track(action: AnalytiksAction) {
                onTrack()
            }

        }

        @Test
        fun `with unregistered service throws ServiceNotFound`() {
            assertThrows<AnalytiksServiceNotFound> { Analytiks.get<TestService>() }
        }

        @Test
        fun `with registered service returns TestService instance`() {
            val service = TestService { }
            Analytiks.registerService(service = service)

            val actual = Analytiks.get<TestService>()

            assertSame(expected = service, actual = actual)
        }
    }

    @Nested
    inner class RegisterService {

        private inner class TestService(override val name: String) : AnalytiksService {
            override fun track(action: AnalytiksAction) {
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

            val failingLambda = { Analytiks.registerService(TestService(name = "TEST_1")) }

            assertThrows<AlreadyRegisteredService> { failingLambda() }
        }

    }

    @Nested
    inner class TrackEvent {

        private val testEvent = AnalytiksAction.Event("TEST_EVENT")

        private inner class TestService(
            override val name: String = "TEST_SERVICE",
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
            override val name: String = "TEST_SERVICE",
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
