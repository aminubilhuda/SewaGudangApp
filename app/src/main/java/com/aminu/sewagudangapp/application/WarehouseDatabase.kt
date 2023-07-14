package com.aminu.sewagudangapp.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.aminu.sewagudangapp.dao.WarehouseDao
import com.aminu.sewagudangapp.model.Warehouse


@Database(entities = [Warehouse::class], version = 2, exportSchema = false)
abstract class WarehouseDatabase: RoomDatabase() {
    abstract fun warehouseDao(): WarehouseDao

    companion object {
        private var INSTANCE: WarehouseDatabase? = null

        // migrasi dartabase dari versi 1 ke versi 2 karena ada perubahan
        private val migration1to2: Migration = object: Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE warehouse_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE warehouse_table ADD COLUMN longitude Double DEFAULT 0.0")
            }

        }
        fun getDatabase(context: Context): WarehouseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WarehouseDatabase::class.java,
                    "warehouse_database"
                )
                    .addMigrations(migration1to2)
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}