package com.jerimkaura.filestore.presentation.add

import androidx.lifecycle.ViewModel
import com.jerimkaura.filestore.data.Client
import com.jerimkaura.filestore.data.ClientDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(var clientDao: ClientDao) : ViewModel() {
    fun insertClient(client: Client) {
        return clientDao.insert(client)
    }
}