package com.jerimkaura.filestore.presentation.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jerimkaura.filestore.R
import com.jerimkaura.filestore.data.local.Client
import com.jerimkaura.filestore.databinding.FragmentAddBinding
import com.jerimkaura.filestore.util.showAlert


class AddFragment : Fragment(R.layout.fragment_add) {
    private var binding: FragmentAddBinding? = null
    private val addViewModel: AddViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
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
                val client = Client(
                    date = System.currentTimeMillis(),
                    name = name,
                    order = order,
                    terms = terms
                )
                addViewModel.insert(client)
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
