// src/main/java/com/neonbridge/MainActivity.kt

package com.neonbridge

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.neonbridge.ui.BridgePrompt

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.start_button)
        startButton.setOnClickListener { onStartClick() }
    }

    private fun onStartClick() {
        BridgePrompt.show(this) { useInternet ->
            if (useInternet) {
                // Fallback logic here
            } else {
                // Proceed with Wi-Fi Direct
            }
        }
    }
}
