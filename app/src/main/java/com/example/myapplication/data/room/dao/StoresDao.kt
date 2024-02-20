package com.example.myapplication.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.room.entities.StoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoresDao {

    @Query("SELECT * FROM stores_table")
    fun getStores(): Flow<List<StoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStore(store:StoreEntity)

    @Query("SELECT * FROM stores_table WHERE id = :id")
    fun getStoreById(id:Long):Flow<StoreEntity>

    @Delete()
    fun deleteStore(store: StoreEntity)
}