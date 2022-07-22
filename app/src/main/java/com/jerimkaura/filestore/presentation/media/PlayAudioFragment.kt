package com.jerimkaura.filestore.presentation.media

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.jerimkaura.filestore.R
import com.jerimkaura.filestore.databinding.FragmentPlayAudioBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PlayAudioFragment : Fragment(R.layout.fragment_play_audio) {
    @Inject
    lateinit var glide: RequestManager

    private val songs = ArrayList<String>()
    private var binding: FragmentPlayAudioBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        binding = FragmentPlayAudioBinding.bind(view)
    }

}
