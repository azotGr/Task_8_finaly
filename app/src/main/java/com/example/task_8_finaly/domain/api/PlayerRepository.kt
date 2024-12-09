package com.example.task_8_finaly.domain.api

import com.example.task_8_finaly.domain.models.Track

interface PlayerRepository {
    fun preparePlayer(track: Track, onTimeUpdate: (String) -> Unit, onCompletion: () -> Unit)
    fun play(onTimeUpdate: (String) -> Unit)
    fun pause()
    fun release()
    fun isPlaying(): Boolean
    fun seekToStart()
    fun hasReachedEnd(): Boolean
    fun getCurrentPosition(): Int
}