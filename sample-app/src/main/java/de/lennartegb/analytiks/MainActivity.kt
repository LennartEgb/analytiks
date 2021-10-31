package de.lennartegb.analytiks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.lennartegb.analytiks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var clickCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonTrack.setOnClickListener {
            Analytiks.track(ButtonClickEvent(++clickCount))
        }
    }

    override fun onResume() {
        super.onResume()
        Analytiks.track(MainScreenView)
    }
}

private val ButtonClickEvent: ((count: Int) -> AnalytiksAction) = { count ->
    AnalytiksAction.Event(
        name = "main_button",
        params = mapOf("count" to count.toString())
    )
}

private val MainScreenView = AnalytiksAction.View(name = "main_screen")
