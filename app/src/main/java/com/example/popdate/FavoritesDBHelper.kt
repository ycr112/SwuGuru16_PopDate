package com.example.popdate

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FavoritesDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // 찜한 목록을 저장할 테이블 생성
        val SQL_CREATE_FAVORITES_TABLE = """
            CREATE TABLE $TABLE_FAVORITES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ITEM TEXT NOT NULL
            )
        """
        db.execSQL(SQL_CREATE_FAVORITES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 데이터베이스 버전 업그레이드 시 호출됨
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITES")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "favorites.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_FAVORITES = "favorites"
        const val COLUMN_ID = "id"
        const val COLUMN_ITEM = "item"
    }
}
