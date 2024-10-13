package com.example.dbhomewor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.dbhomewor.ShoppingListDbHelper;

import org.jetbrains.annotations.Nullable;

public class ShoppingListProvider extends ContentProvider {

    private ShoppingListDbHelper dbHelper;

    // URI Matcher
    private static final int LISTS = 100;
    private static final int PRODUCTS = 101;
    private static final int TYPES = 102;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI("com.example.shoppinglist", "lists", LISTS);
        uriMatcher.addURI("com.example.shoppinglist", "products", PRODUCTS);
        uriMatcher.addURI("com.example.shoppinglist", "types", TYPES);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new ShoppingListDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case LISTS:
                cursor = db.query("Lists", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCTS:
                cursor = db.query("Product", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case TYPES:
                cursor = db.query("Type", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id;

        switch (uriMatcher.match(uri)) {
            case LISTS:
                id = db.insert("Lists", null, values);
                break;
            case PRODUCTS:
                id = db.insert("Product", null, values);
                break;
            case TYPES:
                id = db.insert("Type", null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted;

        switch (uriMatcher.match(uri)) {
            case LISTS:
                rowsDeleted = db.delete("Lists", selection, selectionArgs);
                break;
            case PRODUCTS:
                rowsDeleted = db.delete("Product", selection, selectionArgs);
                break;
            case TYPES:
                rowsDeleted = db.delete("Type", selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated;

        switch (uriMatcher.match(uri)) {
            case LISTS:
                rowsUpdated = db.update("Lists", values, selection, selectionArgs);
                break;
            case PRODUCTS:
                rowsUpdated = db.update("Product", values, selection, selectionArgs);
                break;
            case TYPES:
                rowsUpdated = db.update("Type", values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case LISTS:
                return "vnd.android.cursor.dir/vnd.com.example.shoppinglist.lists";
            case PRODUCTS:
                return "vnd.android.cursor.dir/vnd.com.example.shoppinglist.products";
            case TYPES:
                return "vnd.android.cursor.dir/vnd.com.example.shoppinglist.types";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}