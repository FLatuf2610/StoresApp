package com.example.myapplication.di.RoomModule

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.room.StoreDatabase
import com.example.myapplication.data.room.dao.StoresDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideStoresDatabase(@ApplicationContext context: Context) :StoreDatabase {
        return Room.databaseBuilder(
            context,
            StoreDatabase::class.java,
            "stores_db"
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideStoresDao(db:StoreDatabase):StoresDao = db.getStoresDao()

}