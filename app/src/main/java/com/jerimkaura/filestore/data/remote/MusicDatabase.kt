package com.jerimkaura.filestore.data.remote

import com.jerimkaura.filestore.data.entities.Song

class MusicDatabase {
    private var songs = ArrayList<Song>()

    suspend fun getAllSongs(): List<Song> {
        return try {
            songs.apply {
                add(
                    Song(
                        "234235",
                        "Tarrus Rilley",
                        "She is Loyal",
                        "https://d294.d2mefast.net/tb/2/64/tarrus_riley_she_s_royal_mp3_39543.mp",
                        "https://cdn.pixabay.com/photo/2013/02/22/09/03/singer-84874_1280.jpg"
                    )
                )
                add(
                    Song(
                        "234335",
                        "Zuchu & Diamond",
                        "Zuchu & Diamond",
                        "https://d285.d2mefast.net/tb/9/63/zuchu_sukari_official_music_video_mp3_40213.mp3",
                        "https://cdn.pixabay.com/photo/2013/02/22/09/03/singer-84874_1280.jpg"
                    )
                )
                add(
                    Song(
                        "234435",
                        "Zuchu",
                        "Mtasubiri",
                        "https://d293.d2mefast.net/tb/d/99/diamond_platnumz_ft_zuchu_mtasubiri_music_video_mp3_39918.mp3",
                        "https://cdn.pixabay.com/photo/2013/02/22/09/03/singer-84874_1280.jpg"
                    )
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}