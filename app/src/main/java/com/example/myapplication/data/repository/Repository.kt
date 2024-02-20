package com.example.myapplication.data.repository

import com.example.myapplication.data.room.dao.StoresDao
import com.example.myapplication.data.room.entities.StoreEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(private val dao:StoresDao) {

    fun getStores(): Flow<List<StoreEntity>> =
        dao.getStores().flowOn(Dispatchers.IO).conflate()

    fun getStoreById(id: Long):Flow<StoreEntity> =
        dao.getStoreById(id).flowOn(Dispatchers.IO).conflate()

    suspend fun insertStore(storeEntity: StoreEntity){
        return withContext(Dispatchers.IO){
            dao.insertStore(storeEntity)
        }
    }

    suspend fun deleteStore(storeEntity: StoreEntity){
        return withContext(Dispatchers.IO){
            dao.deleteStore(storeEntity)
        }
    }

}