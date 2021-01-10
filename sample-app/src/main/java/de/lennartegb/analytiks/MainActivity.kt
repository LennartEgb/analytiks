package de.lennartegb.analytiks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.lennartegb.analytiks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonTrack.setOnClickListener {
            Analytiks.track(ButtonClickEvent())
        }
    }

    override fun onResume() {
        super.onResume()
        Analytiks.track(MainScreenView())
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