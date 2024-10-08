package com.example.sixkalimas

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.json.JSONObject
import java.io.IOException

class DisplayActivity : AppCompatActivity() {
    private lateinit var glorifyTextView: TextView
    private lateinit var translationTextView: TextView
    private lateinit var heading: TextView
    private lateinit var backbtn: ImageView
    private lateinit var playButton: ImageView
    private lateinit var pauseButton: ImageView
    private lateinit var underlineGlorify: View
    private lateinit var underlineTranslation: View
    private var kalimaNumber: Int = 1
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        glorifyTextView = findViewById(R.id.glorify)
        translationTextView = findViewById(R.id.eng_urdutranslation)
        heading = findViewById(R.id.heading)
        backbtn = findViewById(R.id.imageView)
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        underlineGlorify = findViewById(R.id.underline_glorify)
        underlineTranslation = findViewById(R.id.underline_translation)
        // Set click listener for back button
        backbtn.setOnClickListener {
            finish()
        }

        kalimaNumber = intent.getIntExtra("kalima_number", 1)

        // Display GlorificationFragment by default
        if (savedInstanceState == null) {
            loadFragment(GlorificationFragment.newInstance(kalimaNumber))
            updateUnderline(glorifyTextView)
        }

        // Set click listeners to switch fragments
        glorifyTextView.setOnClickListener {
            loadFragment(GlorificationFragment.newInstance(kalimaNumber))
            updateUnderline(glorifyTextView)
        }

        translationTextView.setOnClickListener {
            loadFragment(TranslationFragment.newInstance(kalimaNumber))
            updateUnderline(translationTextView)
        }

        // Set click listener for play button
        playButton.setOnClickListener {
            if (isPlaying) {
                pauseAudio()
            } else {
                playAudio()
            }
        }

        // Set click listener for pause button
        pauseButton.setOnClickListener {
            if (isPlaying) {
                pauseAudio()
            }
        }
    }
    private fun updateUnderline(selectedTextView: TextView) {
        underlineGlorify.visibility = if (selectedTextView == glorifyTextView) View.VISIBLE else View.GONE
        underlineTranslation.visibility = if (selectedTextView == translationTextView) View.VISIBLE else View.GONE
    }
    // Function to load Kalima data from JSON
    private fun loadKalimaData(): JSONObject? {
        return try {
            val inputStream = assets.open("kalimas.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charsets.UTF_8)
            JSONObject(json)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    // Function to get the Kalima object from JSON by number
    fun getKalima(kalimaNumber: Int): JSONObject? {
        val kalimaData = loadKalimaData()
        val kalimas = kalimaData?.getJSONArray("kalimas")
        val kalima = kalimas?.getJSONObject(kalimaNumber - 1)
        Log.d("DisplayActivity", "Kalima data for number $kalimaNumber: $kalima")
        return kalima
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun playAudio() {
        if (mediaPlayer == null) {
            val audioResId = getAudioResourceIdForKalima(kalimaNumber)
            if (audioResId != null) {
                mediaPlayer = MediaPlayer.create(this, audioResId)
            }
        }

        mediaPlayer?.apply {
            if (!isPlaying) {
                start()  // Resumes playback from the current position
                this@DisplayActivity.isPlaying = true
                updatePlayButtonIcon()
                setOnCompletionListener {
                    resetMediaPlayer()
                }
            }
        }
    }

    private fun pauseAudio() {
        mediaPlayer?.apply {
            if (isPlaying) {
                pause()  // Pauses playback and saves the current position
                this@DisplayActivity.isPlaying = false
                updatePlayButtonIcon()
            }
        }
    }

    private fun resetMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
        updatePlayButtonIcon()
    }

    private fun updatePlayButtonIcon() {
        if (isPlaying) {
            playButton.setImageResource(R.drawable.pause) // Replace with your pause icon
        } else {
            playButton.setImageResource(R.drawable.play) // Replace with your play icon
        }
    }

    private fun getAudioResourceIdForKalima(kalimaNumber: Int): Int? {
        return when (kalimaNumber) {
            1 -> R.raw.kalima1
            2 -> R.raw.kalima2
            3 -> R.raw.kalima3
            4 -> R.raw.kalima4
            5 -> R.raw.kalima5
            6 -> R.raw.kalima6
            else -> null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        resetMediaPlayer()
    }
}
