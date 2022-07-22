package com.jerimkaura.filestore.repository

import androidx.lifecycle.LiveData
import com.jerimkaura.filestore.data.Song
import com.jerimkaura.filestore.data.SongDao
import javax.inject.Inject

class SongRepository @Inject constructor(private val songDao: SongDao){
    val getAllSongs: LiveData<List<Song>> = songDao.getAllSongs()

    suspend fun addSong(song: Song) {
        songDao.addSong(song)
    }

    fun getSong(id: Int): LiveData<Song> {
        return songDao.getSongById(id)
    }
}