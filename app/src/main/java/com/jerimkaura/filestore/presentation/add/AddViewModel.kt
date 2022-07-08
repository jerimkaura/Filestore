package com.jerimkaura.filestore.presentation.add

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerimkaura.filestore.data.local.Client
import com.jerimkaura.filestore.data.local.ClientDao
import com.jerimkaura.filestore.data.local.ClientDatabase
import com.jerimkaura.filestore.data.repository.ClientRoomRepository
import com.jerimkaura.filestore.domain.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private var clientRepository: ClientRepository, application: Application) :
    ViewModel() {
    init {
        val clientDao = ClientDatabase.invoke(application.applicationContext)
        clientRepository = ClientRoomRepository(clientDao as ClientDao)
    }

    fun insert(client: Client) = viewModelScope.launch {
        clientRepository.insert(client)
    }
}