package com.example.task_8_finaly.presentation.ui.track

import com.example.task_8_finaly.domain.models.Track

fun interface TrackClickListener {
    fun trackClick(track: Track)
}