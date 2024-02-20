package com.example.myapplication.domain

import com.example.myapplication.data.repository.Repository
import com.example.myapplication.data.room.entities.StoreEntity
import javax.inject.Inject

class DeleteStoreUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(storeEntity: StoreEntity) = repository.deleteStore(storeEntity)
}