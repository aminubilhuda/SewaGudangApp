package com.aminu.sewagudangapp.application

import android.app.Application
import com.aminu.sewagudangapp.repository.WarehouseRepository

class WarehouseApp: Application() {
    val database by lazy { WarehouseDatabase.getDatabase(this) }
    val repository by lazy { WarehouseRepository(database.warehouseDao()) }
}