package com.jerimkaura.filestore.presentation.client

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jerimkaura.filestore.databinding.FragmentClientDetailsBinding
import com.jerimkaura.filestore.presentation.clients.ClientsViewModel
import com.jerimkaura.filestore.util.showAlert

class ClientDetailsFragment : Fragment() {
    private val clientsViewModel: ClientsViewModel by viewModels()
    private var binding: FragmentClientDetailsBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClientDetailsBinding.bind(view)
        showAlert(requireContext(), "Client")
    }
}
