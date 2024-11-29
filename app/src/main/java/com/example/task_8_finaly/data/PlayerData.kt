package com.example.task_8_finaly.data

import android.media.MediaPlayer
import com.example.task_8_finaly.domain.models.Track

class PlayerData {
    private var mediaPlayer: MediaPlayer? = null
    private var onCompletionCallback: (() -> Unit)? = null

    fun preparePlayer(track: Track, onCompletion: () -> Unit) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(track.previewUrl)
            prepare()
            setOnCompletionListener {
                onCompletion()
            }
        }
        onCompletionCallback = onCompletion
    }

    fun play() {
        mediaPlayer?.start()
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    fun seekToStart() {
        mediaPlayer?.seekTo(0)
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }
}