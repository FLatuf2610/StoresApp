package com.example.myapplication.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.room.entities.StoreEntity
import com.example.myapplication.domain.DeleteStoreUseCase
import com.example.myapplication.domain.GetStoreByIdUseCase
import com.example.myapplication.ui.home.HomeFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getStoreByIdUseCase: GetStoreByIdUseCase,
    private val deleteStoreUseCase: DeleteStoreUseCase)
    :ViewModel(){
    private val _store = MutableStateFlow(StoreEntity(name="", phone = 0))
    val store:StateFlow<StoreEntity> = _store

    fun getStore(id: Long){
        viewModelScope.launch {
            getStoreByIdUseCase(id).collect {storeFlow ->
                _store.value = storeFlow
            }
        }
    }

    fun deleteStore(storeEntity: StoreEntity){
        viewModelScope.launch {
            deleteStoreUseCase(storeEntity)
        }

    }

    fun editStore(id: Long, navController: NavController){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToAddFragment(id, true))
    }

}