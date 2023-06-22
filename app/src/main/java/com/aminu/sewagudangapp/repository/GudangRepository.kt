package com.aminu.sewagudangapp.repository

import com.aminu.sewagudangapp.dao.GudangDao
import com.aminu.sewagudangapp.model.Gudang
import kotlinx.coroutines.flow.Flow

class GudangRepository(private val gudnagDao: GudangDao) {
    val allGudangs: Flow<List<Gudang>> = gudnagDao.getAllGudang()

    suspend fun insertGudang(gudang: Gudang){
        gudnagDao.insertGudang(gudang)
    }

    suspend fun deleteGudang(gudang: Gudang){
        gudnagDao.deleteGudang(gudang)
    }

    suspend fun updateGudang(gudang: Gudang){
        gudnagDao.updateGudang(gudang)
    }


}