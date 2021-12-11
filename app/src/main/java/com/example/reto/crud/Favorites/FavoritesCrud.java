package com.example.reto.crud.Favorites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.reto.models.Party;

import java.util.ArrayList;
import java.util.List;

public class FavoritesCrud {
    FavoritesHelper dbHelper;

    public FavoritesCrud(Context ctx) {
        dbHelper = new FavoritesHelper(ctx);

    }

    public void add(Party item) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("id", item.getId());
        content.put("name", item.getName());
        content.put("address", item.getAddress());
        content.put("description", item.getDescription());
        content.put("price", item.getPrice());

        database.insert("favorites", null, content);

        database.close();
    }

    public void remove(Integer id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM favorites WHERE id = " + id);
        database.close();
    }

    public boolean exist(Integer id) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor c = database.rawQuery("SELECT id FROM favorites WHERE id = ?", new String[]{String.valueOf(id)});

        boolean response = c.getCount() > 0;

        if (response) {
            c.close();
            database.close();
        }

        database.close();
        return response;
    }

    public int getMaxPrice(){
        int response = 100;

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor c = database.query("favorites", new String [] {"price"}, null, null, null, null, "'price' ASC LIMIT 1");

        try {
            c.moveToFirst();
            response = c.getInt(c.getColumnIndex("price"));
        }catch (Exception ignore){}

        c.close();
        database.close();
        return response;
    }

    public List<Party> getPartiesWhen(int max){

        String selection = null;
        List<Party> response = new ArrayList<>();



        if(max != 0){
            selection = "f.price <= " + max;
        }
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor c = database.query(
                "favorites as f",
                null,
                selection,
                null, null,
                null, null);

        if(c != null && c.moveToFirst()){
            do{
                Party toAdd = new Party();
                toAdd.setId(c.getInt(c.getColumnIndex("id")));
                toAdd.setPrice(c.getInt(c.getColumnIndex("price")));
                toAdd.setName(c.getString(c.getColumnIndex("name")));
                toAdd.setAddress(c.getString(c.getColumnIndex("address")));
                toAdd.setDescription(c.getString(c.getColumnIndex("description")));
                response.add(toAdd);
            }while (c.moveToNext());
            c.close();
            database.close();
        }

        return response;
    }
}
