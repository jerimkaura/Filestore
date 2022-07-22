package com.jerimkaura.filestore.presentation.media


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.util.RepeatModeUtil
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.jerimkaura.filestore.R
import com.jerimkaura.filestore.data.Song
import com.jerimkaura.filestore.databinding.FragmentPlayAudioBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayAudioFragment : Fragment(com.jerimkaura.filestore.R.layout.fragment_play_audio) {
    private var songs = ArrayList<Song>()
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    private val songsViewModel: SongsViewModel by viewModels()
    private var binding: FragmentPlayAudioBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayAudioBinding.bind(view)
        songs.apply {
            add(
                Song(
                    uri = "https://d278.d2mefast.net/tb/a/06/otile_brown_x_jovial_jeraha_official_music_video_mp3_78210.mp3",
                    artist = "Otile Brown",
                    name = "Jeraha",
                ),
            )
            add(
                Song(
                    uri = "https://d284.d2mefast.net/tb/d/1d/diamond_platnumz_naanzaje_official_music_video_mp3_78295.mp3",
                    artist = "Diamond Platnumz",
                    name = "Naazaje",
                ),
            )
            add(
                Song(
                    uri = "https://d279.d2mefast.net/tb/b/d7/otile_brown_x_jovial_such_kinda_love_official_music_video_mp3_78344.mp3",
                    artist = "Otile",
                    name = "Such Kinda Love",
                ),
            )
        }
        arguments?.let { bundle ->
            val passedArgs = PlayAudioFragmentArgs.fromBundle(bundle)
            songsViewModel.getSingleSong(passedArgs.songId).observe(viewLifecycleOwner) { song ->
//                binding!!.tvSongName.text = song.artist.plus(" ${song.name}")
            }
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
//        hideSystemUi()
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
                exoPlayer.playWhenReady
                val mediaItem = MediaItem.fromUri(getString(com.jerimkaura.filestore.R.string.media_url_mp3))
                exoPlayer.setMediaItem(mediaItem)
                songs.map { song ->
                    val songItem = song.uri?.let { MediaItem.fromUri(it) }
                    if (songItem != null) {
                        exoPlayer.addMediaItem(songItem)
                    }
                }
                exoPlayer.apply {
                    playWhenReady = playWhenReady
                    seekTo(currentItem, playbackPosition)
                    shuffleModeEnabled = true
                    repeatMode = RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL
                    prepare()
                    play()
                    binding!!.btnPlayPause.setImageResource(R.drawable.ic_pause_song)
                    updatePlayerPositionProgress()

                }



                binding!!.totalDuration.text = player?.let { getReadableTime(it.duration.toInt()) }
                updatePlayerPositionProgress()
                binding!!.btnPlayPause.setOnClickListener {
                    if (player!!.isPlaying) {
                        player!!.pause().also {
                            binding!!.btnPlayPause.setImageResource(R.drawable.ic_play_audio)
                        }
                    } else {
                        player!!.play().also {
                            binding!!.btnPlayPause.setImageResource(R.drawable.ic_pause_song)
                        }

                    }
                }

                binding!!.btnNextSong.setOnClickListener {
                    exoPlayer.seekToNextMediaItem()
                }
                binding!!.btnPreviousSong.setOnClickListener {
                    exoPlayer.seekToPreviousMediaItem()
                }
                binding!!.seekBar.progress = exoPlayer.currentPosition.toInt()
                binding!!.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                    var progressValue = 0
                    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                        progressValue = seekBar.progress
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar) {
                        seekBar.progress = progressValue
                        binding!!.progressDuration.text = getReadableTime(progressValue)
                        player!!.seekTo(progressValue.toLong())
                    }
                })
            }
    }

    private fun updatePlayerPositionProgress() {
        Handler().postDelayed({
            if (player!!.isPlaying) {
                binding!!.seekBar.max = player!!.duration.toInt()
                binding!!.seekBar.progress = player!!.currentPosition.toInt()
                binding!!.totalDuration.text = getReadableTime(player!!.duration.toInt())
                binding!!.progressDuration.text = getReadableTime(player!!.currentPosition.toInt())
                Log.d("==============>", "initializePlayer: ${player!!.bufferedPercentage}")
            }

            //repeat calling method
            updatePlayerPositionProgress()
        }, 1000)
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

    fun getReadableTime(duration: Int): String {
        val hours = duration / (1000 * 60 * 60)
        val minutes = (duration % (1000 * 60 * 60)) / (1000 * 60)
        val seconds = (((duration % (1000 * 60 * 60)) % (1000 * 60 * 60)) % (1000 * 60)) / 1000

        return if (hours < 1) {
            String.format("%02d:%02d", minutes, seconds)
        } else {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }

    }

}


