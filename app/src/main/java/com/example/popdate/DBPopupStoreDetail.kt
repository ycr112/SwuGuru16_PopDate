package com.example.popdate

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBPopupStoreDetail(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "popupStore.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_POPUP_STORES = "popupStores"
        const val COLUMN_STORE_ID = "storeId"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_PERIOD = "period"
        const val COLUMN_LOCATION = "location"
        const val COLUMN_LINK = "link"
        const val COLUMN_IMAGE_URL = "imageUrl"

        const val TABLE_COMMENTS = "comments"
        const val COLUMN_COMMENT_ID = "commentId"
        const val COLUMN_COMMENT_TEXT = "commentText"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createPopupStoresTable = """
            CREATE TABLE $TABLE_POPUP_STORES (
                $COLUMN_STORE_ID TEXT PRIMARY KEY,
                $COLUMN_NAME TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_PERIOD TEXT,
                $COLUMN_LOCATION TEXT,
                $COLUMN_LINK TEXT,
                $COLUMN_IMAGE_URL TEXT
            )
        """.trimIndent()

        val createCommentsTable = """
            CREATE TABLE $TABLE_COMMENTS (
                $COLUMN_COMMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_STORE_ID TEXT,
                $COLUMN_COMMENT_TEXT TEXT,
                FOREIGN KEY($COLUMN_STORE_ID) REFERENCES $TABLE_POPUP_STORES($COLUMN_STORE_ID)
            )
        """.trimIndent()

        db?.execSQL(createPopupStoresTable)
        db?.execSQL(createCommentsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_POPUP_STORES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_COMMENTS")
        onCreate(db)
    }
}
