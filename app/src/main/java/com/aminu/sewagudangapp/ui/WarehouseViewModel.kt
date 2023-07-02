package com.aminu.sewagudangapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aminu.sewagudangapp.model.Warehouse
import com.aminu.sewagudangapp.repository.WarehouseRepository
import kotlinx.coroutines.launch

class WarehouseViewModel(private val repository: WarehouseRepository): ViewModel() {
    val allWarehouse: LiveData<List<Warehouse>> = repository.allWarehouses.asLiveData()

    fun insert(warehouse: Warehouse) = viewModelScope.launch {
        repository.insertWarehouse(warehouse)
    }
    fun delete(warehouse: Warehouse) = viewModelScope.launch {
        repository.deleteWarehouse(warehouse)
    }
    fun update(warehouse: Warehouse) = viewModelScope.launch {
        repository.updateWarehouse(warehouse)
    }
}

class WarehouseViewModelFactory(private val repository: WarehouseRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((WarehouseViewModel::class.java))) {
            return WarehouseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow View Model Class")
    }
}