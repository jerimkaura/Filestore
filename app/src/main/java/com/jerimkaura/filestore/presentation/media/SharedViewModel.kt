package com.jerimkaura.filestore.presentation.media

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer

class SharedViewModel: ViewModel() {
    private val player = MutableLiveData<ExoPlayer>()

    fun getPlayer(): MutableLiveData<ExoPlayer>? {
        return player
    }
}