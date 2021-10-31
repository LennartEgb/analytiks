package de.lennartegb.analytiks

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class AnalytiksActionTest {

    @Nested
    inner class Event {
        @Test
        fun `with any name and no params returns event with an empty map of params`() {
            assertEquals(
                expected = emptyMap(),
                actual = AnalytiksAction.Event(name = "John Doe").params
            )
        }

        @Test
        fun `with any name and params created by lambda returns event with correct params`() {
            assertEquals(
                expected = mapOf("Hi" to "Sir"),
                actual = AnalytiksAction.Event(name = "John Doe") {
                    put("Hi", "Sir")
                }.params
            )
        }
    }

    @Nested
    inner class View {
        @Test
        fun `with any name and no params returns event with an empty map of params`() {
            assertEquals(
                expected = emptyMap(),
                actual = AnalytiksAction.View(name = "John Doe").params
            )
        }

        @Test
        fun `with any name and params created by lambda returns event with correct params`() {
            assertEquals(
                expected = mapOf("Hi" to "Sir"),
                actual = AnalytiksAction.View(name = "John Doe") {
                    put("Hi", "Sir")
                }.params
            )
        }
    }

}
