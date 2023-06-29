package com.aminu.sewagudangapp.repository

import com.aminu.sewagudangapp.dao.WarehouseDao
import com.aminu.sewagudangapp.model.Warehouse
import kotlinx.coroutines.flow.Flow

class WarehouseRepository(private val gudnagDao: WarehouseDao) {
    val allGudangs: Flow<List<Warehouse>> = gudnagDao.getAllGudang()

    suspend fun insertGudang(warehouse: Warehouse){
        gudnagDao.insertGudang(warehouse)
    }

    suspend fun deleteGudang(warehouse: Warehouse){
        gudnagDao.deleteGudang(warehouse)
    }

    suspend fun updateGudang(warehouse: Warehouse){
        gudnagDao.updateGudang(warehouse)
    }


}