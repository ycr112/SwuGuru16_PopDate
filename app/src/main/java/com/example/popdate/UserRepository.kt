package com.example.popdate

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class UserRepository(context: Context) {

    private val dbHelper = UserDBHelper(context)

    fun addUser(name: String, email: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(UserDBHelper.COLUMN_NAME, name)
            put(UserDBHelper.COLUMN_EMAIL, email)
        }
        db.insert(UserDBHelper.TABLE_USERS, null, values)
        db.close()
    }

    fun getUser(): User? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            UserDBHelper.TABLE_USERS,
            arrayOf(UserDBHelper.COLUMN_NAME, UserDBHelper.COLUMN_EMAIL),
            null, null, null, null, null
        )
        var user: User? = null
        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_NAME))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(UserDBHelper.COLUMN_EMAIL))
            user = User(name, email)
        }
        cursor.close()
        db.close()
        return user
    }
}

data class User(val name: String, val email: String)
