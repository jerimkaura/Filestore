package com.jerimkaura.filestore.presentation.clients

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jerimkaura.filestore.data.entities.Client
import com.jerimkaura.filestore.databinding.ClientItemBinding
import java.util.*

class ClientsAdapter :
    RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder>() {
    private var clients: MutableList<Client> = ArrayList()

    inner class ClientsViewHolder(val clientItemBinding: ClientItemBinding) :
        RecyclerView.ViewHolder(clientItemBinding.root) {
        fun bindItem(client: Client) {
            clientItemBinding.name.text = client.name
            val calendar = Calendar.getInstance()
            val dateFormat = DateFormat.getDateFormat(itemView.context)
            calendar.timeInMillis = client.date
            clientItemBinding.date.text = dateFormat.format(calendar.time)
        }
    }

    fun addClients(items: List<Client>) {
        this.clients.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems(){
        this.clients.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        return ClientsViewHolder(
            ClientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        val client = clients[position]
        holder.bindItem(client)
        holder.clientItemBinding.root.setOnClickListener { view ->
            val action = client.id?.let { clientId ->
               ClientsFragmentDirections.actionClientsFragmentToClientDetailsFragment(clientId)
            }
            if (action != null) {
                view.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return clients.size
    }
}