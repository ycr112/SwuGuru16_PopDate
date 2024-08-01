package com.example.signup

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PopupStoreDao {
    @Insert
    suspend fun insert(popupStore: PopupStore)

    @Insert
    suspend fun insertAll(popupStores: List<PopupStore>)

    @Query("SELECT * FROM popup_store WHERE location = :location")
    suspend fun getPopupStoresByLocation(location: String): List<PopupStore>

    @Query("SELECT * FROM popup_store WHERE category = :category")
    suspend fun getPopupStoresByCategory(category: String): List<PopupStore>
}
