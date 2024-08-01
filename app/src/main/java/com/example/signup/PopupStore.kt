package com.example.signup

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popup_store")
data class PopupStore(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val location: String,
    val category: String
)