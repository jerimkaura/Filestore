package com.jerimkaura.filestore.presentation.add

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
class AddViewModel @Inject constructor(private val repository: ClientRepository) : ViewModel() {
    fun addClient(client: Client){
        viewModelScope.launch {
            repository.addClient(client)
        }
    }
}