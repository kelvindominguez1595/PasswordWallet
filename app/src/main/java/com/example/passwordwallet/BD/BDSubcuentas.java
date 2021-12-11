package com.example.passwordwallet.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDSubcuentas extends SQLiteOpenHelper {
    public BDSubcuentas(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table cuenta_secund(" +
                "id integer primary key autoincrement," +
                "cuentaprincipal_id text not null," +
                "icono_id text not null," +
                "email text not null," +
                "password text not null," +
                "fecha date not null" +
                " )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
