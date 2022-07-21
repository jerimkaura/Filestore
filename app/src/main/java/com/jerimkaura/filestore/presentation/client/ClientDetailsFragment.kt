package com.jerimkaura.filestore.presentation.client

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.jerimkaura.filestore.R
import com.jerimkaura.filestore.databinding.FragmentClientDetailsBinding
import com.jerimkaura.filestore.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileNotFoundException

@AndroidEntryPoint
class ClientDetailsFragment : Fragment(R.layout.fragment_client_details),
    FilesAdapter.OnClickListener {
    private val filesAdapter: FilesAdapter by lazy {
        FilesAdapter(this)
    }
    private var clientId: Int = 0
    private lateinit var inputPFD: ParcelFileDescriptor
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
                        binding!!.btnPicture.visibility = View.VISIBLE

                    } else {
                        binding!!.btnProposal.setOnClickListener { button ->
                            context!!.buildPdf(client)
                            button.visibility = View.INVISIBLE
                            binding!!.btnPicture.visibility = View.VISIBLE
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

                    binding!!.btnPicture.setOnClickListener {
                        val requestFileIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                            .apply {
                                type = "images/*"
                                addCategory(Intent.CATEGORY_OPENABLE)
                            }
                        startActivityForResult(requestFileIntent, PICTURE_REQUEST)
                    }
                }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, returnIntent: Intent?) {
        if (resultCode != Activity.RESULT_OK ||
            requestCode != PICTURE_REQUEST ||
            returnIntent == null
        ) {
            return
        }

        returnIntent.data?.also { returnUri ->
            inputPFD = try {
                activity!!.contentResolver.openFileDescriptor(returnUri, "r")!!
            } catch (e: FileNotFoundException) {
                Log.e("ClientFragment", "File not found", e)
                return
            }
        }

        val fd = inputPFD.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fd)
        inputPFD.close()

        val input = TextInputEditText(requireContext())
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        input.layoutParams = layoutParams
        input.hint = "Picture Name"

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Picture")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val name = input.text.toString()
                context!!.saveImage(image, name, clientId)
                filesAdapter.setItems(context!!.getAllFiles(clientId))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onClick(file: File) {
        val uri: Uri? = try {
            FileProvider.getUriForFile(
                context!!,
                "com.jerimkaura.filestore.fileprovider",
                file
            )
        } catch (e: IllegalArgumentException) {
            Log.e("Single Client fragment", "The selected file cannot be shared: $file")
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

    companion object {
        const val PICTURE_REQUEST = 12
    }
}
