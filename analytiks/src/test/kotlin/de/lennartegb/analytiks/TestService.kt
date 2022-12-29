package de.lennartegb.analytiks

internal class TestService(
    private val onEvent: () -> Unit = {},
    private val onView: () -> Unit = {},
) : AnalytiksService {
    override fun track(action: Action) {
        when (action) {
            is Action.Event -> onEvent()
            is Action.View -> onView()
        }
    }
}
