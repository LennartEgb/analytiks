package de.lennartegb.analytiks

import org.junit.Test
import kotlin.test.assertEquals

internal class AnalytiksActionTest {
    @Test
    fun expectSameEvents_whenCreatedWithDifferentConstructors() {
        val expected = AnalytiksAction.Event(name = "Event", params = mapOf("position" to "1"))
        val actual = AnalytiksAction.Event(name = "Event") { put("position", "1") }
        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun expectSameViews_whenCreatedWithDifferentConstructors() {
        val expected = AnalytiksAction.View(name = "View", params = mapOf("position" to "1"))
        val actual = AnalytiksAction.View(name = "View") { put("position", "1") }
        assertEquals(expected = expected, actual = actual)
    }
}
