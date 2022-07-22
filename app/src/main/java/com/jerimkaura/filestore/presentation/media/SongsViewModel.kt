package com.jerimkaura.filestore.presentation.media

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerimkaura.filestore.data.Song
import com.jerimkaura.filestore.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongsViewModel @Inject constructor(private val songRepository: SongRepository) : ViewModel() {
    private var _getAllSongs: LiveData<List<Song>> = songRepository.getAllSongs
    val getAllSongs: LiveData<List<Song>>
        get() = _getAllSongs

    fun getSingleSong(id:Int): LiveData<Song>{
        return songRepository.getSong(id)
    }

    fun addSong(song: Song){
        viewModelScope.launch {
            songRepository.addSong(song)
        }
    }
}