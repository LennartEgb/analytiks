package de.lennartegb.analytics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.lennartegb.analytics.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonTrack.setOnClickListener {
            Analytics.track(ButtonClickEvent())
        }
    }

    override fun onResume() {
        super.onResume()
        Analytics.track(MainScreenView())
    }
}

private class ButtonClickEvent : AnalyticsEvent {
    override fun getName(): String = "Button Clicked"

    override fun getParameters(): Map<String, String> {
        return super.getParameters().toMutableMap().apply {
            put("This", "That")
        }
    }
}

private class MainScreenView : AnalyticsView {
    override fun getName(): String = "Main screen displayed"

}