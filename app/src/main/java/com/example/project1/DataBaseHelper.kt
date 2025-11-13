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

    fun insertText(text: String, change: Int): Long {
        val db = writableDatabase

        val values = ContentValues().apply {
            put("text", text)
            put("change", change)
        }

        return db.insert("text", null, values)
    }

    fun getText(): Text?{
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT text, change FROM text LIMIT 1", null)

        var text : Text? = null

        if(cursor.moveToFirst()){
            val textt = cursor.getString(cursor.getColumnIndexOrThrow("text"))
            val change = cursor.getInt(cursor.getColumnIndexOrThrow("change"))

            text = Text(textt, change)

            cursor.close()
            db.close()
            return text
        }

        return text
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Stride&Dungeon.db"

        private const val SQL_CREATE_ENTRIES =
            """
            CREATE TABLE text (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                text TEXT NOT NULL,
                change INTEGER
            )
            """

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS text"
    }
}
