package com.jerimkaura.filestore.presentation.media

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.util.RepeatModeUtil
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.jerimkaura.filestore.R
import com.jerimkaura.filestore.databinding.FragmentPlayAudioBinding


class PlayAudioFragment : Fragment(R.layout.fragment_play_audio) {
    private val playbackStateListener: Player.Listener = playbackStateListener()
    private val songs = ArrayList<String>()
    private var binding: FragmentPlayAudioBinding? = null
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        binding = FragmentPlayAudioBinding.bind(view)
        songs.apply {
            add("https://d294.d2mefast.net/tb/2/64/tarrus_riley_she_s_royal_mp3_39543.mp3")
            add("https://d293.d2mefast.net/tb/d/99/diamond_platnumz_ft_zuchu_mtasubiri_music_video_mp3_39918.mp3")
            add("https://d283.d2mefast.net/tb/3/3b/rayvanny_ft_zuchu_number_one_official_video_mp3_40173.mp3")
            add("https://d285.d2mefast.net/tb/9/63/zuchu_sukari_official_music_video_mp3_40213.mp3")
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun initializePlayer() {
        val trackSelector = DefaultTrackSelector(requireContext()).apply {
            setParameters(buildUponParameters().setMaxVideoSizeSd())
        }
        player = ExoPlayer.Builder(requireContext())
            .setTrackSelector(trackSelector)
            .build()
            .also { exoPlayer ->
                binding!!.playerView.player = exoPlayer
                val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))
//                val mediaItem = MediaItem.Builder()
//                    .setUri(getString(R.string.media_url_dash))
//                    .setMimeType(MimeTypes.APPLICATION_MPD)
//                    .build()

                exoPlayer.setMediaItem(mediaItem)
                songs.map { song ->
                    val songItem = MediaItem.fromUri(song)
                    exoPlayer.addMediaItem(songItem)
                }
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.shuffleModeEnabled = true
                exoPlayer.repeatMode = RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL
                exoPlayer.prepare()
                exoPlayer.play()
            }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(activity!!.window, false)
        WindowInsetsControllerCompat(activity!!.window, binding!!.playerView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}



private fun playbackStateListener() = object : Player.Listener {
    override fun onPlaybackStateChanged(playbackState: Int) {
        val stateString: String = when (playbackState) {
            ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
            ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
            ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
            ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
            else -> "UNKNOWN_STATE             -"
        }
        Log.d("===============>", "changed state to $stateString")
    }
}