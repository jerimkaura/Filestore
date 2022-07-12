package com.jerimkaura.filestore.presentation.clients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jerimkaura.filestore.data.Client
import com.jerimkaura.filestore.data.ClientDao
import com.jerimkaura.filestore.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(private val repository: ClientRepository) : ViewModel() {
    fun getAllClient(): List<Client>? {
        return repository.getAllClients()
    }
}
