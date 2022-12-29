package de.lennartegb.analytiks

import org.junit.Test
import kotlin.test.assertEquals

internal class ActionTest {
    @Test
    fun expectSameEvents_whenCreatedWithDifferentConstructors() {
        val expected = Action.Event(name = "Event", params = mapOf("position" to "1"))
        val actual = Action.Event(name = "Event") { put("position", "1") }
        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun expectSameViews_whenCreatedWithDifferentConstructors() {
        val expected = Action.View(name = "View", params = mapOf("position" to "1"))
        val actual = Action.View(name = "View") { put("position", "1") }
        assertEquals(expected = expected, actual = actual)
    }
}
