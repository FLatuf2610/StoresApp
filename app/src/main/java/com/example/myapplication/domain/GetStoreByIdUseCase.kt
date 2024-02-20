package com.example.myapplication.domain

import com.example.myapplication.data.repository.Repository
import javax.inject.Inject

class GetStoreByIdUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id:Long) = repository.getStoreById(id)
}