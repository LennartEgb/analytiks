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
//            Analytiks.track(ButtonClickEvent("That"))
        }
    }

//    override fun onResume() {
//        super.onResume()
//        Analytiks.track(MainScreenView)
//    }
}

//private val ButtonClickEvent: ((String) -> Analytiks.Action) = { value ->
//    Analytiks.Action.Event(
//        name = "Button Clicked",
//        params = mapOf("This" to value)
//    )
//}
//
//private val MainScreenView = Analytiks.Action.View(name = "Main screen displayed")
