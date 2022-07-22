package com.jerimkaura.filestore.presentation.media

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jerimkaura.filestore.databinding.FragmentPlayAudioBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayAudioFragment : Fragment(com.jerimkaura.filestore.R.layout.fragment_play_audio) {
    private val songsViewModel: SongsViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private val songs = ArrayList<String>()
    private var binding: FragmentPlayAudioBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayAudioBinding.bind(view)
        arguments?.let { bundle ->
            val passedArgs = PlayAudioFragmentArgs.fromBundle(bundle)
            songsViewModel.getSingleSong(passedArgs.songId).observe(viewLifecycleOwner) { song ->
                binding!!.tvSongName.text = song.artist.plus(" ${song.name}")
            }
        }
    }

}