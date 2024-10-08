package com.example.sixkalimas

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class SpashScreen : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 2000 // 2 seconds delay
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_spash_screen)
        // Handler to delay the start of the main activity
        Handler().postDelayed({
            // Start the main activity
            startActivity(Intent(this, MainActivity::class.java))
            // Finish the splash activity so the user can't go back to it
            finish()
        }, SPLASH_DELAY)
    }
}