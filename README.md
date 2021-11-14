# Analytiks
Analytiks is a small abstraction of trackable analytics services like Firebase Google Analytics. 
Use it like [Timber](https://github.com/JakeWharton/timber) by creating and registering services 
in you Application class.

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Analytiks.register(DebugService())
        }
    }
}
```

When registering an event of the user or a screen view it is simple as
```kotlin
val event = AnalytiksAction.Event(name = "button_click", params = mapOf("id" to "value"))
Analytiks.track(event)

val view = AnalytiksAction.View(name = "main_screen")
Analytiks.track(view)
```

## Inspiration
The inspiration for analytics handled in this way came from [this blog article](https://medium.com/@nadavfima/how-to-build-better-analytics-with-kotlin-60ab50ce25ac) by Nadav Fima. GitHub repository can be found [here](https://github.com/sofakingforever/solid-kotlin-analytics)
