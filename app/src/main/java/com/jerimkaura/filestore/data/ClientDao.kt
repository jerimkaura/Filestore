package com.jerimkaura.filestore.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ClientDao {
    @Query("SELECT * FROM client ORDER BY id DESC")
    fun getAllClients(): List<Client>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(client: Client)
}