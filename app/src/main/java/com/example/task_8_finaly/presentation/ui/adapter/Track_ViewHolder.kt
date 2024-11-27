package com.example.task_8_finaly.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.task_8_finaly.R
import com.example.task_8_finaly.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale


class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val trackTime: TextView = itemView.findViewById(R.id.trackTime)
    private val trackImage: ImageView = itemView.findViewById(R.id.trackImage)
    private val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())

    fun bind(track: Track) {
        trackName.text = track.trackName
        artistName.text = track.artistName
        trackTime.text = dateFormat.format(track.trackTime)

        val radius =
            itemView.context.resources.getDimensionPixelSize(R.dimen.track_image_corner_radius)
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.placeholder) // Placeholder при загрузке
            .error(R.drawable.placeholder) // Placeholder при ошибке
            .transform(RoundedCorners(radius))

        Glide.with(itemView)
            .load(track.artworkUrl100)
            .apply(requestOptions)
            .into(trackImage)

    }

    companion object {
        fun create(parent: ViewGroup): TrackViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_track, parent, false)
            return TrackViewHolder(view)
        }
    }
}