package com.jerimkaura.filestore.presentation.clients

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jerimkaura.filestore.R
import com.jerimkaura.filestore.databinding.FragmentClientsBinding

class ClientsFragment : Fragment(R.layout.fragment_clients) {
    private var binding: FragmentClientsBinding? = null
    private val clientsViewModel: ClientsViewModel by viewModels()
    private val clientsAdapter: ClientsAdapter by lazy {
        ClientsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClientsBinding.bind(view)
        val clientsRv = binding!!.rvClients
        clientsRv.apply {
            hasFixedSize()
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = clientsAdapter
        }
        clientsViewModel.allClients.observe(viewLifecycleOwner) { clients ->
            clientsAdapter.addClients(clients)
        }
    }
}

