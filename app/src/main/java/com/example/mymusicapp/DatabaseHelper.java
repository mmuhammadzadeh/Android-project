package com.example.mymusicapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "musicApp.db";
    private static final int DATABASE_VERSION = 1;

    // جداول برای موزیک‌ها و کاربران
    public static final String TABLE_MUSIC = "music";
    public static final String TABLE_USER = "user";
    public static final String TABLE_PLAYLIST = "playlist";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_COVER_URL = "cover_url";
    public static final String COLUMN_AUDIO_URL = "audio_url";
    public static final String COLUMN_USER_ID = "user_id"; // برای ارتباط با کاربر
    public static final String COLUMN_PLAYLIST_NAME = "playlist_name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // ایجاد جدول موزیک‌ها
        String CREATE_MUSIC_TABLE = "CREATE TABLE " + TABLE_MUSIC + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_ARTIST + " TEXT, " +
                COLUMN_COVER_URL + " TEXT, " +
                COLUMN_AUDIO_URL + " TEXT, " +
                COLUMN_USER_ID + " TEXT);";

        // ایجاد جدول کاربران
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "user_phone TEXT, " +
                "user_google_id TEXT);";

        // ایجاد جدول پلی‌لیست‌ها
        String CREATE_PLAYLIST_TABLE = "CREATE TABLE " + TABLE_PLAYLIST + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLAYLIST_NAME + " TEXT, " +
                COLUMN_USER_ID + " TEXT);";

        db.execSQL(CREATE_MUSIC_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PLAYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSIC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
        onCreate(db);
    }

    // متد برای ذخیره موزیک
    public void addMusic(Music music, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, music.getTitle());
        values.put(COLUMN_ARTIST, music.getArtist());
        values.put(COLUMN_COVER_URL, music.getCoverUrl());
        values.put(COLUMN_AUDIO_URL, music.getAudioUrl());
        values.put(COLUMN_USER_ID, userId); // ارتباط با کاربر
        db.insert(TABLE_MUSIC, null, values);
        db.close();
    }

    // متد برای ذخیره اطلاعات کاربر
    public void addUser(String username, String phone, String googleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("user_phone", phone);
        values.put("user_google_id", googleId);
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    // متد برای ذخیره پلی‌لیست
    public void addPlaylist(String playlistName, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYLIST_NAME, playlistName);
        values.put(COLUMN_USER_ID, userId);
        db.insert(TABLE_PLAYLIST, null, values);
        db.close();
    }

    // متد برای دریافت موزیک‌های یک کاربر
    public List<Music> getMusicByUser(String userId) {
        List<Music> musicList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MUSIC, null, COLUMN_USER_ID + "=?", new String[]{userId}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Music music = new Music(
                            cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_ARTIST)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_COVER_URL)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_AUDIO_URL))
                    );
                    musicList.add(music);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return musicList;
    }
}

