package com.ribasoftware.marvelproject.dao;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class CharacterProvider extends ContentProvider {
    private static final String PATH ="characters";
    private static final String AUTHORITY = "com.ribasoftware.marvelproject";

    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final Uri CHARACTERS_URI = BASE_URI.withAppendedPath(BASE_URI,  PATH);

    public static final int TYPE_GENERIC = 0;
    public static final int TYPE_ID = 1;


    private UriMatcher mMatcher;
    private CharacterDBHelper mDBHelper;


    @Override
    public boolean onCreate() {
        mDBHelper = new CharacterDBHelper(getContext());
        return true;
    }

    public CharacterProvider() {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTHORITY, PATH, TYPE_GENERIC);
        mMatcher.addURI(AUTHORITY, PATH+ "/#", TYPE_ID);

    }

    @Override
    public String getType(Uri uri) {
        int uriType = mMatcher.match(uri);
        switch (uriType) {
            case TYPE_GENERIC:
                return ContentResolver.CURSOR_DIR_BASE_TYPE +"/" + AUTHORITY;
            case TYPE_ID:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + AUTHORITY;
            default:
                throw new IllegalArgumentException("Invalid URI");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = mMatcher.match(uri);

        if ( uriType == TYPE_ID){
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            long id = ContentUris.parseId(uri);
            int rowsAffected = db.delete(CharacterContract.TB_CHARACTER,
                    CharacterContract._ID + " = ? ", new String[]{String.valueOf(id)});
            db.close();

            if ( rowsAffected == 0){
                throw new RuntimeException("Fail deleting Favorite Character");
            }

            notifyChanges(uri);

            return rowsAffected;
        }else{
            throw  new IllegalArgumentException("Invalid URi for delete ");
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = mMatcher.match(uri);
        if ( uriType == TYPE_GENERIC){
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            try {
                long id = db.insert(CharacterContract.TB_CHARACTER, null, values);
                if (id == -1){
                    throw new RuntimeException("Error inserting Favorite Character");
                }
                notifyChanges(uri);
                return ContentUris.withAppendedId(uri, id);
            }finally {
                db.close();
            }

        }else{
            throw  new IllegalArgumentException("invalid uri while try to insert character as favorite");
        }

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        int uriType = mMatcher.match(uri);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        switch (uriType) {
            case TYPE_GENERIC:
                cursor = db.query(CharacterContract.TB_CHARACTER,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case TYPE_ID:
                long id = ContentUris.parseId(uri);
                cursor = db.query(CharacterContract.TB_CHARACTER,
                        projection, CharacterContract._ID + " = ?",
                        new String[]{String.valueOf(id)}, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Invalid URI in query method");

        }

        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void notifyChanges(Uri uri){
        if ( getContext() != null){
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }
}
