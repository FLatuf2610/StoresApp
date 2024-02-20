package com.example.myapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.room.dao.StoresDao
import com.example.myapplication.data.room.entities.StoreEntity

@Database(entities = [StoreEntity::class], version = 1, exportSchema = false)
abstract class StoreDatabase :RoomDatabase(){
    abstract fun getStoresDao() :StoresDao
}