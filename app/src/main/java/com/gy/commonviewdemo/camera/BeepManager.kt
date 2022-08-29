package com.gy.commonviewdemo.camera

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Vibrator
import android.provider.SyncStateContract
import android.util.Log
import com.gy.commonviewdemo.R
import java.io.Closeable
import java.io.IOException

class BeepManager(private val activity: Activity) : OnCompletionListener,
    MediaPlayer.OnErrorListener, Closeable {

    private var mediaPlayer: MediaPlayer? = null
    private var playBeep = false
    private var vibrate = false

    fun setPlayBeep(playBeep: Boolean) {
        this.playBeep = playBeep
        updatePrefs()
    }

    fun setVibrate(vibrate: Boolean) {
        this.vibrate = vibrate
    }

    @Synchronized
    fun updatePrefs() {
        if (playBeep && mediaPlayer == null) {
            activity.volumeControlStream = AudioManager.STREAM_MUSIC
            mediaPlayer = buildMediaPlayer(activity)
        }
    }

    @Synchronized
    fun playBeepSoundAndVibrate() {
        Log.d("BeepManager", "playBeepSoundAndVibrate playBeep:$playBeep mediaPlayer:$mediaPlayer")
        if (playBeep && mediaPlayer != null) {
            mediaPlayer?.start()
        }
        if (vibrate) {
            val vibrator = activity
                .getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(200L)
        }
    }

    private fun buildMediaPlayer(activity: Context): MediaPlayer {
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnErrorListener(this)
        return try {
            val file = activity.getResources().openRawResourceFd(R.raw.beep_sound);
            // .openRawResourceFd(R.raw.beep);
            file.use { file ->
                mediaPlayer.setDataSource(
                    file.getFileDescriptor(),
                    file.getStartOffset(),
                    file.getLength());
            }
            mediaPlayer.setVolume(0.05f, 0.05f)
            mediaPlayer.prepare()
            mediaPlayer
        } catch (ioe: IOException) {
            Log.e("BeepManager", "media play error$ioe")
            mediaPlayer.release()
            mediaPlayer
        }
    }

    override fun onCompletion(mp: MediaPlayer) {
        // When the beep has finished playing, rewind to queue up another one.
        mp.seekTo(0)
    }

    @Synchronized
    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
            // No need finish although media player got error.
            // activity.finish();
        } else {
            // possibly media player error, so release and recreate
            mp.release()
            updatePrefs()
        }
        return true
    }

    @Synchronized
    override fun close() {
        mediaPlayer?.release()
    }

    init {
        updatePrefs()
    }
}