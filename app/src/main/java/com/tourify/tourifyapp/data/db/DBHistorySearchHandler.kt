package com.tourify.tourifyapp.data.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHistorySearchHandler(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT)")
        db.execSQL(query)
    }

    fun addHistorySearch(
        searchName: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_COL, searchName)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteHistorySearch(searchId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$ID_COL = ?", arrayOf(searchId.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun getAllHistorySearch(): List<HistorySearchItem> {
        val searchList = mutableListOf<HistorySearchItem>()
        val selectQuery = "SELECT * FROM $TABLE_NAME ORDER BY $ID_COL DESC"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.apply {
            if (moveToFirst()) {
                do {
                    val id = getInt(getColumnIndex(ID_COL))
                    val searchName = getString(getColumnIndex(NAME_COL))
                    val searchItem = HistorySearchItem(id, searchName)
                    searchList.add(searchItem)
                } while (moveToNext())
            }
            close()
        }

        return searchList
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "tourifyDb"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "historySearch"
        private const val ID_COL = "id"
        private const val NAME_COL = "name"
    }
}