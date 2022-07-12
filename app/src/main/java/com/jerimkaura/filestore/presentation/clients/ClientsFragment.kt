package com.jerimkaura.filestore.presentation.clients

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jerimkaura.filestore.R
import com.jerimkaura.filestore.databinding.FragmentClientsBinding


class ClientsFragment : Fragment(R.layout.fragment_clients) {
    private var binding: FragmentClientsBinding? = null
    private val clientsAdapter: ClientsAdapter by lazy {
        ClientsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClientsBinding.bind(view)
    }
}

