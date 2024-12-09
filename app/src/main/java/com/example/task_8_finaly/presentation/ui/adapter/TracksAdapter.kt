package com.example.task_8_finaly.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task_8_finaly.R
import com.example.task_8_finaly.domain.models.Track
import com.example.task_8_finaly.presentation.ui.track.TrackClickListener

class TracksAdapter(private val listener: TrackClickListener): RecyclerView.Adapter<TrackViewHolder>() {
    var tracks = ArrayList<Track>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(view)
    }
    override fun getItemCount(): Int {
        return tracks.size
    }
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener { listener.trackClick(tracks[position]) }
    }
}