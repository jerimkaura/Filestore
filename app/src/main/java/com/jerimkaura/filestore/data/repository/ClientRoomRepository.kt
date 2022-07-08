package com.jerimkaura.filestore.data.repository

import androidx.lifecycle.LiveData
import com.jerimkaura.filestore.data.local.Client
import com.jerimkaura.filestore.data.local.ClientDao
import com.jerimkaura.filestore.domain.repository.ClientRepository

class ClientRoomRepository(private val clientDao: ClientDao) : ClientRepository {
    override fun getAllClients(): LiveData<List<Client>> {
        return clientDao.getAllClients()
    }

    override fun getClient(id: Int): LiveData<Client> {
        return clientDao.getClient(id)
    }

    override suspend fun insert(client: Client) {
        clientDao.insert(client)
    }
}