package com.example.homework5.adapters

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
//import com.example.homework5.database.WorksDatabaseDAO

/********************************
 * Класс относится к Homework6.1
 * Content provider
 ********************************/

//private lateinit var dao: WorksDatabaseDAO

class CarWorksProvider: ContentProvider() {



    override fun onCreate(): Boolean {
        // инициализация БД
//        dao = CarsDatabase.init(context!!).getWorkDatabaseDAO()
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {

        /*val cursor: Cursor? = context?.contentResolver?.query(BuiltInRestrictedAlphabets.table.getUri(),
                null,
                where.where(),
                where.whereArgs(),
                where.limit())*/

        return null
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
}