package com.example.myapplication.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("stores_table")
data class StoreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")val id:Long = 0,
    @ColumnInfo("name")val name:String,
    @ColumnInfo("image")val image:String = "https://images.samsung.com/is/image/samsung/assets/ar/samsung-experience-store/locations/580x320-Palmas-del-Pilar.jpg?\$624_N_JPG\$",
    @ColumnInfo("web")val web:String = "",
    @ColumnInfo("phone")val phone:Long,
    @ColumnInfo("isFavourite")val isFavourite:Boolean = false
)
