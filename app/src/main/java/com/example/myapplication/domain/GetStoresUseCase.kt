package com.example.myapplication.domain

import com.example.myapplication.data.repository.Repository
import com.example.myapplication.data.room.entities.StoreEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoresUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() : Flow<List<StoreEntity>> = repository.getStores()
}