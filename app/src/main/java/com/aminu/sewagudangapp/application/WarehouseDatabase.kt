package com.aminu.sewagudangapp.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aminu.sewagudangapp.dao.WarehouseDao
import com.aminu.sewagudangapp.model.Warehouse


@Database(entities = [Warehouse::class], version = 1, exportSchema = false)
abstract class WarehouseDatabase: RoomDatabase() {
    abstract fun warehouseDao(): WarehouseDao

    companion object {
        private var INSTANCE: WarehouseDatabase? = null

        fun getDatabase(context: Context): WarehouseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WarehouseDatabase::class.java,
                    "warehouse_database"
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}