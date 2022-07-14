package com.jerimkaura.filestore.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Client(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var date: Long,
    var name: String,
    var order: String,
    var terms: String
)
