package com.jerimkaura.filestore.presentation.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerimkaura.filestore.data.Client
import com.jerimkaura.filestore.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleClientViewModel @Inject constructor(private val repository: ClientRepository) :
    ViewModel() {

    fun getClient(id: Int): LiveData<Client> {
        return repository.getClient(id)
    }
}