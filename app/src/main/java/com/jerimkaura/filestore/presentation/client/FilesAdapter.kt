package com.jerimkaura.filestore.presentation.client

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.jerimkaura.filestore.databinding.FileItemBinding
import java.io.File
import java.util.*

class FilesAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<FilesAdapter.FilesViewHolder>() {
    private var allFiles: MutableList<File> = ArrayList()

    var context: Context? = null

    inner class FilesViewHolder(private val fileItemBinding: FileItemBinding) :
        RecyclerView.ViewHolder(fileItemBinding.root) {
        fun bindFile(file: File, clickListener: OnClickListener) {
            val calendar = Calendar.getInstance()
            val dateFormat = DateFormat.getDateFormat(itemView.context)
            calendar.timeInMillis = file.lastModified()
            fileItemBinding.date.text = dateFormat.format(calendar.time)
            fileItemBinding.name.text = file.name
            fileItemBinding.root.setOnClickListener { clickListener.onClick(file) }
        }
    }

    fun setItems(items: List<File>) {
        this.allFiles.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.allFiles.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        context = parent.context
        return FilesViewHolder(
            FileItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        val file = allFiles[position]
        holder.bindFile(file, onClickListener)
    }

    override fun getItemCount(): Int {
        return allFiles.size
    }

    interface OnClickListener {
        fun onClick(file: File)
    }
}