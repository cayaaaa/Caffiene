package com.psi.caffeine.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu")
data class Menu(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "menu_id")
    val menuId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "is_halal")
    val isHalal: Boolean
)
