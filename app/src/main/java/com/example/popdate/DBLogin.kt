package com.example.popdate

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor

class DBLogin(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val TABLE_USERS = "Users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_EMAIL TEXT,"
                + "$COLUMN_PASSWORD TEXT" + ")")
        db.execSQL(createUsersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(email: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_PASSWORD, password)

        val success = db.insert(TABLE_USERS, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    fun getUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(TABLE_USERS, arrayOf(COLUMN_ID), "$COLUMN_EMAIL=? AND $COLUMN_PASSWORD=?",
            arrayOf(email, password), null, null, null, null)
        val count = cursor.count
        cursor.close()
        db.close()
        return count > 0
    }
}
