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
    @Query("SELECT * FROM warehouse_table ORDER BY name ASC")
    fun getAllWarehouse(): Flow<List<Warehouse>>

    @Insert
    suspend fun insertWarehouse(warehouse: Warehouse)

    @Delete
    suspend fun deleteWarehouse(warehouse: Warehouse)

    @Update fun updateWarehouse(warehouse: Warehouse)

}