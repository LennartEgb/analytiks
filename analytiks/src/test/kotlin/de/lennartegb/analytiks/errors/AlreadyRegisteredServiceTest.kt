package de.lennartegb.analytiks.errors

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class AlreadyRegisteredServiceTest {

    @Nested
    inner class GetMessage {

        @Test
        fun `with no message passed returns default message`() {
            assertEquals(
                expected = "Only different services can be registered.",
                actual = AlreadyRegisteredService().message,
            )
        }

        @Test
        fun `with message passed returns default message added to message`() {
            assertEquals(
                expected = "Hi. Only different services can be registered.",
                actual = AlreadyRegisteredService(message = "Hi.").message,
            )
        }
    }
}