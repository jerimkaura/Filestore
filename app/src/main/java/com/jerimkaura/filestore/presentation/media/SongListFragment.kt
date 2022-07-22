package com.jerimkaura.filestore.presentation.media

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jerimkaura.filestore.R
import com.jerimkaura.filestore.data.Song
import com.jerimkaura.filestore.databinding.FragmentSongListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongListFragment : Fragment(R.layout.fragment_song_list) {
    private val songViewModel: SongsViewModel by viewModels()
    private val songsAdapter: SongsAdapter by lazy {
        SongsAdapter()
    }
    private var binding: FragmentSongListBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSongListBinding.bind(view)
        val rvSongs = binding!!.recyclerview
        val songs = listOf(
            Song(
                uri = "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3",
                artist = "Zuchu",
                name = "Sukari",
            ),
            Song(
                uri = "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3",
                artist = "Tarrus Riley",
                name = "She is Loyal",
            ),
            Song(
                uri = "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3",
                artist = "Zuchu & Diamond",
                name = "Number One",
            ),
        )
        songs.forEach {
            songViewModel.addSong(it)
        }
        songViewModel.getAllSongs.observe(viewLifecycleOwner) { songs ->
            songsAdapter.clearItems()
            songsAdapter.addItems(songs)
            rvSongs.apply {
                hasFixedSize()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = songsAdapter
            }
        }
    }
}