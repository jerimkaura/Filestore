package com.jerimkaura.filestore.presentation.clients

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jerimkaura.filestore.R
import com.jerimkaura.filestore.databinding.FragmentClientsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ClientsFragment : Fragment(R.layout.fragment_clients) {
    private var binding: FragmentClientsBinding? = null
    private val clientsViewModel: ClientsViewModel by viewModels()
    private val clientsAdapter: ClientsAdapter by lazy {
        ClientsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClientsBinding.bind(view)
        val rvClients = binding!!.rvClients
        clientsViewModel.getAllClient.observe(viewLifecycleOwner) { clients ->
            clientsAdapter.clearItems()
            clientsAdapter.addClients(clients)
            rvClients.apply {
                hasFixedSize()
                layoutManager = GridLayoutManager(
                    requireContext(),
                    2
                )
                adapter = clientsAdapter
            }
        }

    }
}

