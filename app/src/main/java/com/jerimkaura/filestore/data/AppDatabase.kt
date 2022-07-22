package com.jerimkaura.filestore.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jerimkaura.filestore.data.entities.Client

@Database(entities = [Client::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getClientDao(): ClientDao

    companion object {
        private var db_instance: AppDatabase? = null

        fun getAppDbInstance(context: Context): AppDatabase {
            if (db_instance == null) {
                db_instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).allowMainThreadQueries().build()
            }

            return db_instance!!
        }
    }
}