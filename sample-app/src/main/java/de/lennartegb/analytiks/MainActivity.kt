package de.lennartegb.analytiks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ComposeView(this)
        setContentView(view)
        view.setContent {
            var counter by rememberSaveable { mutableStateOf(0) }
            Scaffold {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Button(onClick = { Analytiks.track(ButtonClickEvent(++counter))}) {
                        Text(text = "Track Event")
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Analytiks.track(MainScreenView)
    }
}

private val ButtonClickEvent: ((count: Int) -> Action) = { count ->
    Action.Event(
        name = "main_button",
        params = {
            put("count", count.toString())
        }
    )
}

private val MainScreenView = Action.View(name = "main_screen")
