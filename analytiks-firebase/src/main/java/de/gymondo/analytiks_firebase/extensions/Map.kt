package de.gymondo.analytiks_firebase.extensions

import android.os.Bundle

fun Map<String, String>.toBundle(): Bundle {
    return Bundle().apply {
        this@toBundle.forEach { (t, u) -> putString(t, u) }
    }
}