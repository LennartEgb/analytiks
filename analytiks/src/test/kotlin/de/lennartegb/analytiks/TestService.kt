package de.lennartegb.analytiks

internal class TestService(
    private val onEvent: () -> Unit = {},
    private val onView: () -> Unit = {},
) : AnalytiksService {
    override fun track(action: AnalytiksAction) {
        when (action) {
            is AnalytiksAction.Event -> onEvent()
            is AnalytiksAction.View -> onView()
        }
    }
}
