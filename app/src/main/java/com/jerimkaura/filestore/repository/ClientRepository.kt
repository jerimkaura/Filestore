package com.jerimkaura.filestore.repository

import com.jerimkaura.filestore.data.Client
import com.jerimkaura.filestore.data.ClientDao
import javax.inject.Inject

class ClientRepository @Inject constructor(private val clientDao: ClientDao) {
    fun getAllClients(): List<Client>? {
        return clientDao.getAllClients()
    }

    fun insert(client: Client) {
        return clientDao.insert(client)
    }
}