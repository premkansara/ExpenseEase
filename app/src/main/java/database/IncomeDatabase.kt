package database

import Income
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class IncomeDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "IncomeEase.db"
        const val DATABASE_VERSION = 1
        const val TABLE_INCOME = "Income"
        const val COLUMN_ID = "id"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_SOURCE = "source"
        const val COLUMN_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_INCOME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_AMOUNT REAL, $COLUMN_SOURCE TEXT, $COLUMN_NOTE TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_INCOME")
        onCreate(db)
    }

    fun addIncome(income: Income) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_AMOUNT, income.amount)
            put(COLUMN_SOURCE, income.source)
            put(COLUMN_NOTE, income.note)
        }
        db.insert(TABLE_INCOME, null, contentValues)
        db.close()
    }
}
