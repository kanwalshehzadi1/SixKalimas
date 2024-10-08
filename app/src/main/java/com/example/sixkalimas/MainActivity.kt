package com.example.sixkalimas

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.box1).setOnClickListener {
            navigateToDisplayActivity(1)
        }

        findViewById<View>(R.id.box2).setOnClickListener {
            navigateToDisplayActivity(2)
        }

        findViewById<View>(R.id.box3).setOnClickListener {
            navigateToDisplayActivity(3)
        }

        findViewById<View>(R.id.box4).setOnClickListener {
            navigateToDisplayActivity(4)
        }

        findViewById<View>(R.id.box5).setOnClickListener {
            navigateToDisplayActivity(5)
        }

        findViewById<View>(R.id.box6).setOnClickListener {
            navigateToDisplayActivity(6)
        }
    }

    private fun navigateToDisplayActivity(kalimaNumber: Int) {
        val intent = Intent(this, DisplayActivity::class.java)
        intent.putExtra("kalima_number", kalimaNumber)
        startActivity(intent)
    }
}