package com.example.popdate

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class FavoritesRepository(context: Context) {

    private val dbHelper = FavoritesDbHelper(context)

    fun addFavorite(item: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(FavoritesDbHelper.COLUMN_ITEM, item)
        }
        db.insert(FavoritesDbHelper.TABLE_FAVORITES, null, values)
        db.close()
    }

    fun getFavorites(): List<String> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            FavoritesDbHelper.TABLE_FAVORITES,
            arrayOf(FavoritesDbHelper.COLUMN_ITEM),
            null, null, null, null, null
        )
        val favorites = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val item = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesDbHelper.COLUMN_ITEM))
            favorites.add(item)
        }
        cursor.close()
        db.close()
        return favorites
    }
}
