package com.jerimkaura.filestore.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Song(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var uri: String? = "",
    var artist: String = "",
    var name: String = "",
    var duration: String? = "04:34",
    var size: Int = 0,
    var albumUri: String? = null
): Serializable