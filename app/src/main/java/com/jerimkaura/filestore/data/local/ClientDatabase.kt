package com.jerimkaura.filestore.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Client::class], version = 1)
abstract class ClientDatabase : RoomDatabase() {
    abstract fun getClientDao(): ClientDao

    companion object {
        @Volatile //observe changes from other threads when a thread changes the db
        private var instance: ClientDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            // Everything happening in this block cannot be accessed
            //  by other threads at the same time
            if(instance == null){
                instance = createDatabase(context)
            }
        }

        private fun createDatabase(context: Context): ClientDatabase? {
            instance = Room.databaseBuilder(
                context.applicationContext,
                ClientDatabase::class.java,
                "client_database"
            ).build()
            return instance
        }
    }
}