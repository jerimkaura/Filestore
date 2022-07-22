package com.jerimkaura.filestore.musicservice

import android.media.MediaMetadata.METADATA_KEY_MEDIA_ID
import android.media.MediaMetadataRetriever.METADATA_KEY_TITLE
import android.net.Uri
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.*
import androidx.core.net.toUri
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.jerimkaura.filestore.data.remote.MusicDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import javax.inject.Inject

class DataSource @Inject constructor(private var musicDatabase: MusicDatabase) {
    private var songs = emptyList<MediaMetadataCompat>()
    private val allSongs = musicDatabase.getAllSongs()

    suspend fun fetchMediaData() = withContext(Dispatchers.IO) {
        state = State.STATE_INITIALIZING
//        val allSongs = musicDatabase.getAllSongs()
        songs = allSongs.map { song ->
            Builder()
                .putString(METADATA_KEY_ARTIST, song.artist)
                .putString(METADATA_KEY_MEDIA_ID, song.mediaId)
                .putString(METADATA_KEY_TITLE.toString(), song.title)
                .putString(METADATA_KEY_DISPLAY_TITLE, song.title)
                .putString(METADATA_KEY_DISPLAY_ICON_URI, song.imageUrl)
                .putString(METADATA_KEY_MEDIA_URI, song.songUrl)
                .putString(METADATA_KEY_ALBUM_ART_URI, song.imageUrl)
                .putString(METADATA_KEY_DISPLAY_SUBTITLE, song.artist)
                .putString(METADATA_KEY_DISPLAY_DESCRIPTION, song.artist)
                .build()
        }
        state = State.STATE_INITIALIZED
    }

    fun asMediaSource(dataSourceFactory: DefaultDataSourceFactory): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        allSongs.forEach { song ->
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)

        }
        return concatenatingMediaSource
    }

    private fun buildMediaSource(uris: ArrayList<Uri>): ConcatenatingMediaSource {
        val userAgent = Util.getUserAgent(, "MusicPlayer")
        val defaultMediaSource = DefaultDataSourceFactory(this, userAgent)
        val progressiveMediaSource = ProgressiveMediaSource.Factory(defaultMediaSource)
        val mediaSources = ArrayList<MediaSource>()

        for (uri in uris) {
            mediaSources.add(progressiveMediaSource.createMediaSource(uri))
        }

        val concatenatingMediaSource = ConcatenatingMediaSource()
        concatenatingMediaSource.addMediaSources(mediaSources)

        return concatenatingMediaSource
    }

    fun asMediaItems() = songs.map { song ->
        val desc = MediaDescriptionCompat.Builder()
            .setMediaUri(song.getString(METADATA_KEY_MEDIA_URI).toUri())
            .setTitle(song.description.title)
            .setSubtitle(song.description.subtitle)
            .setMediaId(song.description.mediaId)
            .setIconUri(song.description.iconUri)
            .build()
        MediaBrowserCompat.MediaItem(desc, FLAG_PLAYABLE)
    }

    private val onReadyListeners = mutableListOf<(Boolean) -> Unit>()

    private var state: State = State.STATE_CREATED
        /**
         * If the music source is initialized or an error occurred then nothing more will happened
         * So we need to call all the onReadyListeners in the onReadyListenersList and change the
         * Value of the states from multiple threads at once
         * */
        set(value) {
            if (value == State.STATE_INITIALIZED || value == State.STATE_ERROR) {
                /**
                 * We use synchronised so that whatever happens to the onReadyListners cannot be
                 * Accessed by any other thing
                 * */
                synchronized(onReadyListeners) {
                    field = state
                    onReadyListeners.forEach { listener ->
                        listener(state == State.STATE_INITIALIZED)
                    }
                }
            } else {
                field = value
            }
        }

    fun whenReady(action: (Boolean) -> Unit): Boolean {
        /**
         * If the music source is not fully loaded then we schedule that job for later by adding it
         * to the onReadyListeners return false because the music source is not ready
         * */
        return if (state == State.STATE_CREATED || state == State.STATE_INITIALIZING) {
            onReadyListeners += action
            false
        } else {
            action(state == State.STATE_INITIALIZED)
            true
        }
    }
}

enum class State {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
}