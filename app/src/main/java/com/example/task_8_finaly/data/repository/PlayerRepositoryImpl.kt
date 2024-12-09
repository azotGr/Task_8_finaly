package com.example.task_8_finaly.data

import android.media.MediaPlayer
import com.example.task_8_finaly.domain.api.PlayerRepository
import com.example.task_8_finaly.domain.models.Track

class PlayerRepositoryImpl : PlayerRepository{
    private var mediaPlayer: MediaPlayer? = null
    private var onCompletionCallback: (() -> Unit)? = null
    private var hasReachedEnd = false

    override fun preparePlayer(track: Track,onTimeUpdate: (String) -> Unit, onCompletion: () -> Unit) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(track.previewUrl)
            prepare()
            setOnCompletionListener {
                onCompletion()
                hasReachedEnd = true
            }
        }
        onCompletionCallback = onCompletion
    }

    override fun play(onTimeUpdate: (String) -> Unit) {
        mediaPlayer?.start()
    }

    override fun pause() {
        mediaPlayer?.pause()
    }

    override fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    override fun seekToStart() {
        mediaPlayer?.seekTo(0)
    }

    override fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    override fun hasReachedEnd(): Boolean {
        return mediaPlayer?.isPlaying == false && mediaPlayer?.currentPosition == mediaPlayer?.duration
    }
}