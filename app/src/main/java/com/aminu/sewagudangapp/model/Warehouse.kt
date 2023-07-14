package com.aminu.sewagudangapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "warehouse_table")
data class Warehouse (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val address: String,
    val owner: String,
    val latitude: Double?,
    val longitude: Double?
) : Parcelable