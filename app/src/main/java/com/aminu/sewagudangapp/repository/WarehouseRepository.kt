package com.aminu.sewagudangapp.repository

import com.aminu.sewagudangapp.dao.WarehouseDao
import com.aminu.sewagudangapp.model.Warehouse
import kotlinx.coroutines.flow.Flow

class WarehouseRepository(private val gudnagDao: WarehouseDao) {
    val allWarehouses: Flow<List<Warehouse>> = gudnagDao.getAllWarehouse()

    suspend fun insertWarehouse(warehouse: Warehouse){
        gudnagDao.insertWarehouse(warehouse)
    }

    suspend fun deleteWarehouse(warehouse: Warehouse){
        gudnagDao.deleteWarehouse(warehouse)
    }

    suspend fun updateWarehouse(warehouse: Warehouse){
        gudnagDao.updateWarehouse(warehouse)
    }


}