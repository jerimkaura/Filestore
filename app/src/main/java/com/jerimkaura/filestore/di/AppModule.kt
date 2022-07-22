package com.jerimkaura.filestore.di

import android.content.Context
import com.jerimkaura.filestore.data.AppDatabase
import com.jerimkaura.filestore.data.ClientDao
import com.jerimkaura.filestore.data.SongDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideDatabaseInstance(@ApplicationContext context: Context): AppDatabase{
        return AppDatabase.getAppDbInstance(context)
    }

    @Singleton
    @Provides
    fun getClientDao(appDatabase: AppDatabase): ClientDao{
        return  appDatabase.getClientDao()
    }

    @Singleton
    @Provides
    fun getSongDao(appDatabase: AppDatabase): SongDao{
        return  appDatabase.getSongDao()
    }
}