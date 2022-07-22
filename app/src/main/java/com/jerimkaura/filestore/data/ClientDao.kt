package com.jerimkaura.filestore.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jerimkaura.filestore.data.entities.Client

@Dao
interface ClientDao {
    @Query("SELECT * FROM client ORDER BY id DESC")
    fun getAllClients(): LiveData<List<Client>>

    @Query("SELECT * FROM client WHERE id=:id ")
    fun getClientById(id: Int): LiveData<Client>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addClient(client: Client): Long

    @Delete
    suspend fun deleteClient(client: Client)

    @Update
    suspend fun updateClient(client: Client)
}