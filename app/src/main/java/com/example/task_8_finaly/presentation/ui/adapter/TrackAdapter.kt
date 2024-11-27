package com.example.task_8_finaly.presentation.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task_8_finaly.domain.models.Track

class TrackAdapter(
    private var tracks: List<Track>,
    private val onItemClickListener: ((Track) -> Unit)? = null
) : RecyclerView.Adapter<TrackViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]
        holder.bind(track)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(track)
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    fun updateTracks(newTracks: List<Track>) {
        tracks = newTracks
        notifyDataSetChanged()
    }
}