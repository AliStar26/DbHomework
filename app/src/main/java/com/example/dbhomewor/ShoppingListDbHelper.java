package com.example.dbhomewor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShoppingListDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shoppingList.db";
    private static final int DATABASE_VERSION = 1;

    // Конструктор
    public ShoppingListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создание таблицы Lists
        String SQL_CREATE_LISTS_TABLE = "CREATE TABLE Lists (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "date INTEGER NOT NULL, " +
                "description TEXT);";

        // Создание таблицы Type
        String SQL_CREATE_TYPE_TABLE = "CREATE TABLE Type (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "label TEXT NOT NULL, " +
                "rule TEXT NOT NULL);";

        // Создание таблицы Product
        String SQL_CREATE_PRODUCT_TABLE = "CREATE TABLE Product (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "count REAL NOT NULL, " +
                "list_id INTEGER NOT NULL, " +
                "checked INTEGER NOT NULL, " +
                "count_type INTEGER NOT NULL, " +
                "FOREIGN KEY (list_id) REFERENCES Lists(_id), " +
                "FOREIGN KEY (count_type) REFERENCES Type(_id));";

        // Выполняем создание таблиц
        db.execSQL(SQL_CREATE_LISTS_TABLE);
        db.execSQL(SQL_CREATE_TYPE_TABLE);
        db.execSQL(SQL_CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаление старых таблиц при обновлении базы данных
        db.execSQL("DROP TABLE IF EXISTS Lists");
        db.execSQL("DROP TABLE IF EXISTS Type");
        db.execSQL("DROP TABLE IF EXISTS Product");
        onCreate(db);
    }
}
