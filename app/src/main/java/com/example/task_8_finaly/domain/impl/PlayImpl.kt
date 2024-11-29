package com.example.task_8_finaly.domain.impl

import com.example.task_8_finaly.data.PlayerData
import com.example.task_8_finaly.domain.api.PlayInter
import com.example.task_8_finaly.domain.api.UIHandler
import com.example.task_8_finaly.domain.models.Track

class PlayImpl(
    private val playerData: PlayerData,
    private val uiHandler: UIHandler // Интерфейс для UI обновлений
) : PlayInter {

    private var hasReachedEnd = false

    override fun preparePlayer(
        track: Track,
        onTimeUpdate: (String) -> Unit,
        onCompletion: () -> Unit
    ) {
        hasReachedEnd = false
        playerData.preparePlayer(track) {
            hasReachedEnd = true
            uiHandler.stopUpdating() // Уведомляем UI-слой остановить обновления
            onCompletion()
        }

        uiHandler.startUpdating {
            val currentPosition = playerData.getCurrentPosition() / 1000
            val formattedTime = String.format("%02d:%02d", currentPosition / 60, currentPosition % 60)
            onTimeUpdate(formattedTime)
        }
    }

    override fun play(onTimeUpdate: (String) -> Unit) {
        if (hasReachedEnd) {
            seekToStart()
        }
        playerData.play()
        uiHandler.resumeUpdating()
    }

    override fun pause() {
        playerData.pause()
        uiHandler.stopUpdating()
    }

    override fun release() {
        playerData.release()
        uiHandler.stopUpdating()
    }

    override fun isPlaying(): Boolean {
        return playerData.isPlaying()
    }

    override fun seekToStart() {
        playerData.seekToStart()
        hasReachedEnd = false
    }

    override fun hasReachedEnd(): Boolean {
        return hasReachedEnd
    }
}