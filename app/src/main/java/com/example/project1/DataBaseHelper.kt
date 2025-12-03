package com.example.project1

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }


    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "text.db"

        private const val SQL_CREATE_ENTRIES =
            """
            CREATE TABLE equipment (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                itemId TEXT NOT NULL,
                itemQuantity INTEGER
            )
            """

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS equipment"
    }
}
