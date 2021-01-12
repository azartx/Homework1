package com.example.homework5.adapters

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.homework5.database.CarsDatabase
import com.example.homework5.database.WorksDatabaseDAO

/********************************
 * Класс относится к Homework6.1
 * Content provider
 ********************************/

private lateinit var dao: WorksDatabaseDAO

class CarWorksProvider : ContentProvider() {

    companion object{
        private const val AUTHORITY = "com.example.homework5.adapters.CarWorksProvider"
        private const val URI_USER_CODE = 1
        private val uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY,"database", URI_USER_CODE)
        }
    }

    override fun onCreate(): Boolean {
        // инициализация БД
        dao = CarsDatabase.init(context!!).getWorkDatabaseDAO()
        return false
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {

        if(uriMatcher.match(uri) == URI_USER_CODE) {
            return dao.getWorksList()
        }
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }
}