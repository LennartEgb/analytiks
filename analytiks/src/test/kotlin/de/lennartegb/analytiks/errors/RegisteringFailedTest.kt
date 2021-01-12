package de.lennartegb.analytiks.errors

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class RegisteringFailedTest {

    @Nested
    inner class GetMessage {

        @Test
        fun `with no message passed returns default message`() {
            assertEquals(
                expected = "Failure while trying to register.",
                actual = RegisteringFailed().message,
            )
        }

        @Test
        fun `with message passed returns default message added to message`() {
            assertEquals(
                expected = "Hi. Failure while trying to register.",
                actual = RegisteringFailed(message = "Hi.").message,
            )
        }
    }

}