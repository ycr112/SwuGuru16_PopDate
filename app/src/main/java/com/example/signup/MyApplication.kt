package com.example.signup

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MyApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    // database를 lazy delegate로 초기화
    val database by lazy { AppDatabase.getDatabase(this) }
    val popupStoreDao by lazy { database.popupStoreDao() }

    override fun onCreate() {
        super.onCreate()
        populateDatabase()
    }

    private fun populateDatabase() {
        applicationScope.launch(Dispatchers.IO) {
            val stores = listOf(
                PopupStore(name = "Store 1", location = "Seoul", category = "Clothing"),
                PopupStore(name = "Store 2", location = "Busan", category = "Dessert")
            )
            popupStoreDao.insertAll(stores)
        }
    }
}