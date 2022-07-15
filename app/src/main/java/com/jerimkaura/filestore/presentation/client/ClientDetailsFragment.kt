package com.jerimkaura.filestore.presentation.client

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jerimkaura.filestore.R
import com.jerimkaura.filestore.databinding.FragmentClientDetailsBinding
import com.jerimkaura.filestore.util.buildPdf
import com.jerimkaura.filestore.util.getAllFiles
import com.jerimkaura.filestore.util.proposalExists
import com.jerimkaura.filestore.util.showAlert
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ClientDetailsFragment : Fragment(R.layout.fragment_client_details),
    FilesAdapter.OnClickListener {
    private val filesAdapter: FilesAdapter by lazy {
        FilesAdapter(this)
    }
    private var clientId: Int = 0
    private val singleClientViewModel: SingleClientViewModel by viewModels()
    private var binding: FragmentClientDetailsBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClientDetailsBinding.bind(view)
        arguments?.let { bundle ->
            val passedArguments = ClientDetailsFragmentArgs.fromBundle(bundle)
            singleClientViewModel.getClient(passedArguments.clientId)
                .observe(viewLifecycleOwner) { client ->
                    binding!!.name.text = client.name
                    binding!!.date.text = client.date.toString()
                    binding!!.order.text = client.order
                    binding!!.terms.text = client.terms
                    clientId = client.id!!

                    context?.let { filesAdapter.setItems(context!!.getAllFiles(clientId)) }

                    if (context!!.proposalExists(clientId)) {
                        binding!!.btnProposal.visibility = View.INVISIBLE

                    } else {
                        binding!!.btnProposal.setOnClickListener { button ->
                            context!!.buildPdf(client)
                            button.visibility = View.INVISIBLE
                            context?.let { filesAdapter.setItems(context!!.getAllFiles(clientId)) }
                        }
                    }

                    binding!!.rvFiles.apply {
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )

                        adapter = filesAdapter

                    }

                }
        }
    }

    override fun onClick(file: File) {
        val uri: Uri? = try {
            FileProvider.getUriForFile(
                context!!,
                "com.jerimkaura.filestore.fileprovider",
                file
            )
        } catch (e: IllegalArgumentException) {
            Log.e("File fragment", "The selected file cannot be shared: $file")
            null
        }

        if (uri != null) {
            showAlert(requireContext(), "$uri")
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(
                uri,
                context!!.contentResolver.getType(uri)
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        }
    }
}
