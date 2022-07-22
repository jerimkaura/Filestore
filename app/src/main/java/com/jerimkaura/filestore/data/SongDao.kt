package com.jerimkaura.filestore.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SongDao {
    @Query("SELECT * FROM song ORDER BY id DESC")
    fun getAllSongs(): LiveData<List<Song>>

    @Query("SELECT * FROM song WHERE id=:id ")
    fun getSongById(id: Int): LiveData<Song>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSong(song: Song): Long
}