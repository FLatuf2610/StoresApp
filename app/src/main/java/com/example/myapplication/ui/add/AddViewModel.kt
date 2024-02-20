package com.example.myapplication.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.room.entities.StoreEntity
import com.example.myapplication.domain.GetStoreByIdUseCase
import com.example.myapplication.domain.InsertStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val insertStoreUseCase: InsertStoreUseCase,
    private val getStoreByIdUseCase: GetStoreByIdUseCase)
    :ViewModel(){
        private val _store:MutableStateFlow<StoreEntity> = MutableStateFlow(StoreEntity(name = "", phone = 0))
        val store:StateFlow<StoreEntity> = _store

        fun getStoreById(id:Long){
            viewModelScope.launch {
                getStoreByIdUseCase(id).collect {
                    _store.emit(it)
                }
            }
        }

        fun insertStore(storeEntity: StoreEntity){
            viewModelScope.launch {
                insertStoreUseCase(storeEntity)
            }
        }

}