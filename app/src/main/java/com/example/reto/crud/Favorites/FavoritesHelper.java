package com.example.reto.crud.Favorites;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class FavoritesHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Parties";
    public static final String FAVORITES_TABLE = "favorites";

    public FavoritesHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + FAVORITES_TABLE + "(" +
                "id INTEGER PRIMARY KEY ," +
                "name TEXT NOT NULL ," +
                "address TEXT NOT NULL," +
                "image BlOB ," +
                "price INTEGER ," +
                "description TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
