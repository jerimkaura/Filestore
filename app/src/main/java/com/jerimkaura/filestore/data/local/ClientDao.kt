package com.jerimkaura.filestore.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ClientDao {
    @Query("SELECT * FROM clients ORDER BY date DESC")
    fun getAllClients(): LiveData<List<Client>>

    @Query("SELECT * FROM clients WHERE id = :id")
    fun getClient(id: Int): LiveData<Client>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(client: Client)
}