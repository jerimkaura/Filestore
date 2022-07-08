package com.jerimkaura.filestore.presentation.client

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jerimkaura.filestore.databinding.FragmentClientBinding

class ClientFragment : Fragment() {
    private var binding: FragmentClientBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClientBinding.bind(view)
    }
}
