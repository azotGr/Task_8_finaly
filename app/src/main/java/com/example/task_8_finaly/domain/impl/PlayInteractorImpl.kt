package com.example.task_8_finaly.domain.impl
import com.example.task_8_finaly.domain.api.PlayInteractor
import com.example.task_8_finaly.domain.api.PlayerRepository
import com.example.task_8_finaly.domain.api.UIHandler
import com.example.task_8_finaly.domain.models.Track

class PlayInteractorImpl(
    private val playerRepository: PlayerRepository, // Интерфейс вместо конкретного PlayerData
    private val uiHandler: UIHandler // Интерфейс для UI обновлений
) : PlayInteractor {


    override fun preparePlayer(
        track: Track,
        onTimeUpdate: (String) -> Unit,
        onCompletion: () -> Unit
    ) {
        playerRepository.preparePlayer(track, onTimeUpdate) {
            uiHandler.stopUpdating() // Уведомляем UI-слой остановить обновления
            onCompletion()
        }

        uiHandler.startUpdating {
            val currentPosition = playerRepository.getCurrentPosition() / 1000
            val formattedTime = String.format("%02d:%02d", currentPosition / 60, currentPosition % 60)
            onTimeUpdate(formattedTime)
        }
    }

    override fun play(onTimeUpdate: (String) -> Unit) {
        if (playerRepository.hasReachedEnd()) {
            seekToStart()
        }
        playerRepository.play(onTimeUpdate)
        uiHandler.resumeUpdating()
    }

    override fun pause() {
        playerRepository.pause()
        uiHandler.stopUpdating()
    }

    override fun release() {
        playerRepository.release()
        uiHandler.stopUpdating()
    }

    override fun isPlaying(): Boolean {
        return playerRepository.isPlaying()
    }

    override fun seekToStart() {
        playerRepository.seekToStart()
    }

    override fun hasReachedEnd(): Boolean {
        return playerRepository.hasReachedEnd()
    }

    override fun getCurrentPosition(): Int {
        return playerRepository.getCurrentPosition()
    }
}