package database

import Expense
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "ExpenseEase.db"
        const val DATABASE_VERSION = 1
        const val TABLE_EXPENSE = "Expense"
        const val COLUMN_ID = "id"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_TAG = "tag"
        const val COLUMN_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_EXPENSE ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_AMOUNT REAL, $COLUMN_CATEGORY TEXT, $COLUMN_TAG TEXT, $COLUMN_NOTE TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSE")
        onCreate(db)
    }

    fun addExpense(expense: Expense) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_AMOUNT, expense.amount)
            put(COLUMN_CATEGORY, expense.category)
            put(COLUMN_TAG, expense.tag)
            put(COLUMN_NOTE, expense.note)
        }
        db.insert(TABLE_EXPENSE, null, contentValues)
        db.close()
    }

    fun deleteExpense(expenseId: Int) {
        val db = writableDatabase
        db.delete(TABLE_EXPENSE, "$COLUMN_ID = ?", arrayOf(expenseId.toString()))
        db.close()
    }

    fun updateExpense(expense: Expense) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_AMOUNT, expense.amount)
            put(COLUMN_CATEGORY, expense.category)
            put(COLUMN_TAG, expense.tag)
            put(COLUMN_NOTE, expense.note)
        }
        db.update(TABLE_EXPENSE, contentValues, "$COLUMN_ID = ?", arrayOf(expense.id.toString()))
        db.close()
    }
}
