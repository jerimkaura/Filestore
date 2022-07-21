package com.jerimkaura.filestore.presentation.add

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jerimkaura.filestore.MainActivity
import com.jerimkaura.filestore.R
import com.jerimkaura.filestore.data.Client
import com.jerimkaura.filestore.databinding.FragmentAddBinding
import com.jerimkaura.filestore.util.showAlert
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {
    private val workManager = activity?.applicationContext?.let { WorkManager.getInstance(it) }
    private val addViewModel: AddViewModel by viewModels()
    private var binding: FragmentAddBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
        val workRequest =
            PeriodicWorkRequestBuilder<SimpleWorker>(
                5,
                TimeUnit.MINUTES,
                1,
                TimeUnit.MINUTES
            ).build()
        workManager?.enqueue(workRequest)
        binding!!.btnSave.setOnClickListener {
            val name = binding!!.txtName.text.toString()
            val order = binding!!.txtOrder.text.toString()
            val terms = binding!!.txtTerms.text.toString()

            if (name.isBlank()) {
                showAlert(requireContext(), "Name cannot be blank.")
            } else if (order.isBlank()) {
                showAlert(requireContext(), "Order cannot be blank.")
            } else if (terms.isBlank()) {
                showAlert(requireContext(), "Terms cannot be blank.")
            } else {
                val client = Client( date = System.currentTimeMillis(), name = name, order = order, terms = terms)
                addViewModel.addClient(client)
                (activity as MainActivity).hideKeyboard()
                val navController = Navigation.findNavController(view)
                navController.navigate(AddFragmentDirections.actionAddFragmentToClientsFragment())
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}

