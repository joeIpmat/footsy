package com.footsy.footsy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

/**
 * Created by Joe on 11/10/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Scores.db";
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /** Scores Table **/
        final String CreateScoresTable = "CREATE TABLE " + DatabaseContract.SCORES_TABLE + " ("
                + DatabaseContract.ScoresTable._ID + " INTEGER PRIMARY KEY,"
                + DatabaseContract.ScoresTable.DATE_COL + " TEXT NOT NULL,"
                + DatabaseContract.ScoresTable.TIME_COL + " INTEGER NOT NULL,"
                + DatabaseContract.ScoresTable.HOME_COL + " TEXT NOT NULL,"
                + DatabaseContract.ScoresTable.AWAY_COL + " TEXT NOT NULL,"
                + DatabaseContract.ScoresTable.LEAGUE_COL + " INTEGER NOT NULL,"
                + DatabaseContract.ScoresTable.HOME_GOALS_COL + " TEXT NOT NULL,"
                + DatabaseContract.ScoresTable.AWAY_GOALS_COL + " TEXT NOT NULL,"
                + DatabaseContract.ScoresTable.MATCH_ID + " INTEGER NOT NULL,"
                + DatabaseContract.ScoresTable.MATCH_DAY + " INTEGER NOT NULL,"
                + " UNIQUE (" + DatabaseContract.ScoresTable.MATCH_ID + ") ON CONFLICT REPLACE"
                + " );";
        db.execSQL(CreateScoresTable);

        /** Head2Head Table **/
        final String CreateHead2HeadTable = "CREATE TABLE " + DatabaseContract.TABLE_HEAD2HEAD + " ("
				+ DatabaseContract.ScoresTable._ID + " INTEGER PRIMARY KEY,"
                + DatabaseContract.H2hTable.MATCH_ID + " INTEGER NOT NULL,"
				+ DatabaseContract.H2hTable.MATCH_DATE + " TEXT NOT NULL,"
                + DatabaseContract.H2hTable.HOME_TEAM_WINS + " INTEGER,"
                + DatabaseContract.H2hTable.AWAY_TEAM_WINS + " INTEGER,"
                + DatabaseContract.H2hTable.DRAWS + " INTEGER,"
				+ DatabaseContract.H2hTable.AWAY_TEAM + " INTEGER,"
				+ DatabaseContract.H2hTable.HOME_TEAM + " INTEGER,"
				+ DatabaseContract.H2hTable.GOAL_AWAY_TEAM + " INTEGER,"
				+ DatabaseContract.H2hTable.GOAL_HOME_TEAM + " INTEGER"
				+ " UNIQUE (" + DatabaseContract.H2hTable.MATCH_ID + ") ON CONFLICT REPLACE"
				+ " );";
        db.execSQL(CreateHead2HeadTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Remove old values when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.SCORES_TABLE);
    }
}
