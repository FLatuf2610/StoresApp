package com.example.myapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.room.entities.StoreEntity
import com.example.myapplication.domain.GetStoresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStoresUseCase: GetStoresUseCase
) :ViewModel() {

        private val _stores = MutableStateFlow<List<StoreEntity>>(emptyList())
        val stores :StateFlow<List<StoreEntity>> = _stores

        private fun getStores(){
            viewModelScope.launch {
                getStoresUseCase().collect{ result ->
                    if (result.isEmpty()) {
                        _stores.emit(emptyList())
                    }
                    else {
                        _stores.emit(result)
                    }
                }
            }
        }

        fun onCreateViewModel(){
            getStores()
        }

}