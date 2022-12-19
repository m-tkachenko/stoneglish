package pl.salo.stoneglish.domain.use_cases.dictionary

import android.media.MediaPlayer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.util.Constants

class PlayAudioByUrl {
    private lateinit var player: MediaPlayer
    operator fun invoke(url: String?): Flow<Resource<Unit>> = callbackFlow {
        try {
            if (url == null) throw Exception(Constants.SOMETHING_WENT_WRONG)
            delay(500) //time to make the progress bar visible

            player = MediaPlayer().apply {
                setDataSource(url)
                prepare()
                start()
            }

            val listener = MediaPlayer.OnCompletionListener {
                trySend(Resource.Success(Unit)).isSuccess
            }
            player.setOnCompletionListener(listener)

            awaitClose {
                player.cancel()
            }
        } catch (e: Exception) {
            trySend(Resource.Error(null, e.message)).isSuccess
            awaitClose {
                if (this@PlayAudioByUrl::player.isInitialized) {
                    player.cancel()
                }
            }
        }
    }

    private fun MediaPlayer.cancel() {
        this.stop()
        this.reset()
        this.release()
    }

}
