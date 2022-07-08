package com.jerimkaura.filestore.presentation.clients

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jerimkaura.filestore.data.local.Client
import com.jerimkaura.filestore.data.local.ClientDao
import com.jerimkaura.filestore.data.local.ClientDatabase
import com.jerimkaura.filestore.data.repository.ClientRoomRepository
import com.jerimkaura.filestore.domain.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(private var clientRepository: ClientRepository, application: Application) :
    ViewModel() {
    init {
        val clientDao = ClientDatabase.invoke(application.applicationContext)
        clientRepository = ClientRoomRepository(clientDao as ClientDao)
    }

    val allClients: LiveData<List<Client>> = clientRepository.getAllClients()
}