package com.ribasoftware.marvelproject.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ribamar on 06/10/16.
 */

public class CharacterDBHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "dbCharacter";

    public CharacterDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder createTableCharacter = new StringBuilder();

        db.execSQL(createTableCharacter
                .append("CREATE TABLE " + CharacterContract.TB_CHARACTER + "( ")
                .append(  CharacterContract._ID             + " INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(  CharacterContract.COL_ID_WEB      + " INTEGER UNIQUE NOT NULL,")
                .append(  CharacterContract.COL_NAME        + " TEXT NOT NULL,")
                .append(  CharacterContract.COL_DESCRIPTION + " TEXT, ")
                .append(  CharacterContract.COL_MODIFIED    + " TEXT, ")
                .append(  CharacterContract.COL_THUMBNAIL   + " TEXT )")
                .toString());
//
//        createTableCharacter.setLength(0);
//
//
//        db.execSQL(createTableCharacter
//                .append("CREATE TABLE " + ComicContract.TB_COMIC + "(")
//                .append(    ComicContract._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, ")
//
//              .toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
