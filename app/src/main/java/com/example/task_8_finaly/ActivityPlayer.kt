package com.example.task_8_finaly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class ActivityPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val track = intent.getSerializableExtra("track") as Track

        val coverImageView: ImageView = findViewById(R.id.player_image)
        val trackText: TextView = findViewById(R.id.track_name)
        val artistText: TextView = findViewById(R.id.artist_name)
        val collectionText: TextView = findViewById(R.id.album_name)
        val dateText: TextView = findViewById(R.id.year)
        val genreText: TextView = findViewById(R.id.genre)
        val countryText: TextView = findViewById(R.id.country)
        val trackTimeText: TextView = findViewById(R.id.time)
        val backButton: Button = findViewById(R.id.button_back)
        val currentPlayTimeTextView: TextView = findViewById(R.id.current_time)

        trackText.text = track.trackName
        artistText.text = track.artistName
        collectionText.text = track.collectionName ?: "Unknown Album"
        dateText.text = getYear(track.releaseDate)
        genreText.text = track.primaryGenreName
        countryText.text = track.country
        trackTimeText.text = trackTime(track.trackTime)
        currentPlayTimeTextView.text = "0:00"

        // Картинка обложки
        val radius = resources.getDimensionPixelSize(R.dimen.player_image_corner_radius)
        Glide.with(this)
            .load(track.artworkUrl100.replace("100x100bb", "512x512bb"))
            .apply(RequestOptions().transform(RoundedCorners(radius)))
            .into(coverImageView)

        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun trackTime(trackTime: Long): String {
        val minutes = (trackTime / 1000) / 60
        val seconds = (trackTime / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun getYear(dateString: String): String {
        return dateString.take(4) //Первые 4 символа (год)
    }
}