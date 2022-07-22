package com.jerimkaura.filestore.repository

import androidx.lifecycle.LiveData
import com.jerimkaura.filestore.data.entities.Client
import com.jerimkaura.filestore.data.ClientDao
import javax.inject.Inject

class ClientRepository @Inject constructor(private val clientDao: ClientDao) {
    val getAllClients: LiveData<List<Client>> = clientDao.getAllClients()

    suspend fun addClient(client: Client) {
        clientDao.addClient(client)
    }

    suspend fun deleteClient(client: Client) {
        clientDao.deleteClient(client)
    }

    suspend fun updateClient(client: Client) {
        clientDao.updateClient(client)
    }

    fun getClient(id: Int): LiveData<Client>{
        return clientDao.getClientById(id)
    }

}