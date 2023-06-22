package com.aminu.sewagudangapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aminu.sewagudangapp.model.Gudang
import kotlinx.coroutines.flow.Flow


@Dao
interface GudangDao {
    @Query("SELECT * FROM `gudang_table` ORDER BY name ASC")
    fun getAllGudang(): Flow<List<Gudang>>

    @Insert
    suspend fun insertGudang(gudang: Gudang)

    @Delete
    suspend fun deleteGudang(gudang: Gudang)

    @Update fun updateGudang(gudang: Gudang)

}