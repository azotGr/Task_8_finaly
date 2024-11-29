import com.example.task_8_finaly.data.PlayerData
import com.example.task_8_finaly.domain.api.PlayInter
import com.example.task_8_finaly.domain.models.Track

class PlayerRepositoryImpl(private val playerData: PlayerData) : PlayInter {

    override fun preparePlayer(track: Track, onTimeUpdate: (String) -> Unit, onCompletion: () -> Unit) {
        playerData.preparePlayer(track, onCompletion)
    }

    override fun play(onTimeUpdate: (String) -> Unit) {
        playerData.play()
    }

    override fun pause() {
        playerData.pause()
    }

    override fun release() {
        playerData.release()
    }

    override fun isPlaying(): Boolean {
        return playerData.isPlaying()
    }

    override fun seekToStart() {
        playerData.seekToStart()
    }

    override fun getCurrentPosition(): Int {
        return playerData.getCurrentPosition()
    }

    override fun hasReachedEnd(): Boolean {
        return playerData.hasReachedEnd()
    }

}