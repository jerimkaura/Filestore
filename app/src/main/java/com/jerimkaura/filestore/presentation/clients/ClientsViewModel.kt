package com.jerimkaura.filestore.presentation.clients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerimkaura.filestore.data.Client
import com.jerimkaura.filestore.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(private val repository: ClientRepository) : ViewModel() {
    private var _getAllClients : LiveData<List<Client>> = repository.getAllClients

    val getAllClient: LiveData<List<Client>>
        get() = _getAllClients

    init {

    }

    fun getSingleClient(id: Int) = viewModelScope.launch {
        repository.getClient(id)
    }
}
