package com.aminu.sewagudangapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aminu.sewagudangapp.model.Warehouse
import kotlinx.coroutines.flow.Flow


@Dao
interface WarehouseDao {
    @Query("SELECT * FROM `gudang_table` ORDER BY name ASC")
    fun getAllGudang(): Flow<List<Warehouse>>

    @Insert
    suspend fun insertGudang(warehouse: Warehouse)

    @Delete
    suspend fun deleteGudang(warehouse: Warehouse)

    @Update fun updateGudang(warehouse: Warehouse)

}