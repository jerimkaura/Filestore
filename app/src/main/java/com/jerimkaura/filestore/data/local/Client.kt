package com.jerimkaura.filestore.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class Client(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var date: Long,
    var name: String,
    var order: String,
    var terms: String
)
