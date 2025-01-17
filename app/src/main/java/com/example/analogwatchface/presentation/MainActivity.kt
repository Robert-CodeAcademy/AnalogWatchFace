// MainActivity.kt (Launcher Activity)
package com.example.analogwatchface

import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finish() // This activity is not used for watch faces but is required.
    }
}