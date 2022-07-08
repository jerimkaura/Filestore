package com.jerimkaura.filestore.domain.repository

import androidx.lifecycle.LiveData
import com.jerimkaura.filestore.data.local.Client

interface ClientRepository {
    fun getAllClients(): LiveData<List<Client>>

    fun getClient(id: Int): LiveData<Client>

    suspend fun insert(client: Client)
}