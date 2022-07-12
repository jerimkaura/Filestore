package com.jerimkaura.filestore.presentation.clients

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jerimkaura.filestore.data.Client
import com.jerimkaura.filestore.databinding.ClientItemBinding
import java.util.*
import kotlin.collections.ArrayList

class ClientsAdapter:
    RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder>() {
    inner class ClientsViewHolder(private val clientItemBinding: ClientItemBinding) :
        RecyclerView.ViewHolder(clientItemBinding.root) {
        fun bindItem(client: Client) {
            clientItemBinding.name.text = client.name
            val calendar = Calendar.getInstance()
            val dateFormat = DateFormat.getDateFormat(itemView.context)
            calendar.timeInMillis = client.date
            clientItemBinding.date.text = dateFormat.format(calendar.time)
        }

    }

    private var clients : List<Client> = ArrayList()

    fun addClients(items: List<Client>) {
        this.clients = items
        notifyDataSetChanged()
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Client>() {
        override fun areItemsTheSame(oldItem: Client, newItem: Client): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Client, newItem: Client): Boolean {
            return oldItem == newItem
        }

    }

    // getting the two lists and comparing them
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        return ClientsViewHolder(
            ClientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        val client = differ.currentList[position]
        holder.bindItem(client)
        setOnClickListener {
            onItemClickListener?.let { it(client) }
        }
    }

    override fun getItemCount(): Int {
        // Getting item count from our list differ
        return differ.currentList.size
    }

    private var onItemClickListener: ((Client) -> Unit)? = null

    private fun setOnClickListener(listener: ((Client)) -> Unit) {
        onItemClickListener = listener
    }

}