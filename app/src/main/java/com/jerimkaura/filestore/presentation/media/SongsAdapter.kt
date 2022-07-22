package com.jerimkaura.filestore.presentation.media

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jerimkaura.filestore.data.Song
import com.jerimkaura.filestore.databinding.SongItemBinding


class SongsAdapter :
    RecyclerView.Adapter<SongsAdapter.SongsViewHolder>() {
    private var songs: MutableList<Song> = ArrayList()

    var context: Context? = null

    inner class SongsViewHolder(val songItemBiding: SongItemBinding) :
        RecyclerView.ViewHolder(songItemBiding.root) {
        fun bindSong(song: Song) {
            songItemBiding.tvSongName.text = song.name
            songItemBiding.tvSongDuration.text = song.duration.toString()
//            Glide.with(context!!).load(song.uri).centerCrop()
//                .placeholder(R.drawable.song_thumbnail).into(songItemBiding.imageViewSongArt)
        }
    }

    fun addItems(items: List<Song>) {
        this.songs.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems(){
        this.songs.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val context = parent.context
        return SongsViewHolder(
            SongItemBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val song = songs[position]
        holder.bindSong(song)
        holder.songItemBiding.root.setOnClickListener { view ->
            val action = song.id?.let { songId ->
                SongListFragmentDirections.actionSongListFragmentToPlayAudioFragment(songId)
            }
            if (action != null) {
                view.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return songs.size
    }

}