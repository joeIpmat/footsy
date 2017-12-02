package com.footsy.footsy;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Joe on 11/10/17.
 */

public class ScoresProvider extends ContentProvider {

    private static DBHelper mOpenHelper;
    private static final int MATCHES = 100;
    private static final int MATCHES_WITH_LEAGUE = 101;
    private static final int MATCHES_WITH_ID = 102;
    private static final int MATCHES_WITH_DATE = 103;
	private static final int MATCHES_H2H = 104;

    private UriMatcher mUriMatcher = buildUriMatcher();

    private static final String SCORES_BY_LEAGUE = DatabaseContract.ScoresTable.LEAGUE_COL + " = ?";
    private static final String SCORES_BY_DATE = DatabaseContract.ScoresTable.DATE_COL + " LIKE ?";
    private static final String SCORES_BY_ID = DatabaseContract.ScoresTable.MATCH_ID + " = ?";
	private static final String SCORES_H2H = DatabaseContract.H2hTable.MATCH_ID + " = ?";

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.BASE_CONTENT_URI.toString();
        matcher.addURI(authority, null, MATCHES);
        matcher.addURI(authority, "league", MATCHES_WITH_LEAGUE);
        matcher.addURI(authority, "id", MATCHES_WITH_ID);
        matcher.addURI(authority, "date", MATCHES_WITH_DATE);
		matcher.addURI(authority, "h2h", MATCHES_H2H);
        return matcher;
    }

    private int match_uri(Uri uri) {
        String link = uri.toString();
        if (link.contentEquals(DatabaseContract.BASE_CONTENT_URI.toString())) {
            return MATCHES;
        }
        else if (link.contentEquals(DatabaseContract.ScoresTable.buildScoreWithDate().toString())) {
            return MATCHES_WITH_DATE;
        }
        else if (link.contentEquals(DatabaseContract.H2hTable.buildH2hWithID().toString())) {
			return MATCHES_H2H;
		}
        return -1;
    }

    //prepares content provider
    @Override
    public boolean onCreate() {
        mOpenHelper = new DBHelper(getContext());
        return false;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    //return mime type of this uri
    @Override
    public String getType(Uri uri) {
        final int match = mUriMatcher.match(uri);
        switch (match) {
            case MATCHES:
                return DatabaseContract.ScoresTable.CONTENT_TYPE;
            case MATCHES_WITH_LEAGUE:
                return DatabaseContract.ScoresTable.CONTENT_TYPE;
            case MATCHES_WITH_ID:
                return DatabaseContract.ScoresTable.CONTENT_ITEM_TYPE;
            case MATCHES_WITH_DATE:
                return DatabaseContract.ScoresTable.CONTENT_TYPE;
			case MATCHES_H2H:
				return DatabaseContract.H2hTable.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri :" + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        int match = match_uri(uri);
        switch (match) {
            case MATCHES:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.SCORES_TABLE,
                        projection, null, null, null, null, sortOrder);
                break;
            case MATCHES_WITH_DATE:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.SCORES_TABLE,
                        projection, SCORES_BY_DATE, selectionArgs, null, null, sortOrder);
                break;
            case MATCHES_WITH_ID:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.SCORES_TABLE,
                        projection, SCORES_BY_ID, selectionArgs, null, null, sortOrder);
                break;
            case MATCHES_WITH_LEAGUE:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.SCORES_TABLE,
                        projection, SCORES_BY_LEAGUE, selectionArgs, null, null, sortOrder);
                break;
			case MATCHES_H2H:
				retCursor = mOpenHelper.getReadableDatabase().query(
						DatabaseContract.TABLE_HEAD2HEAD, projection, null, null,
						null, null, sortOrder);
//				retCursor = mOpenHelper.getReadableDatabase().query(
//						DatabaseContract.TABLE_HEAD2HEAD,
//						projection, SCORES_H2H, selectionArgs, null, null, sortOrder);
				break;
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (match_uri(uri)) {
            case MATCHES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long id = db.insertWithOnConflict(DatabaseContract.SCORES_TABLE, null, value,
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

			case MATCHES_H2H:
				db.beginTransaction();
				returnCount = 0;
				try {
                    for (ContentValues value : values) {
                        long id = db.insertWithOnConflict(DatabaseContract.TABLE_HEAD2HEAD, null, value,
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (id != -1) {
                            returnCount++;
                        } else {
                            throw new SQLException("Failed to insert row into " + uri);
                        }
                    }
                    db.setTransactionSuccessful();
                } catch (IllegalStateException e) {
				    e.printStackTrace();
				} finally {
					db.endTransaction();
				}
				getContext().getContentResolver().notifyChange(uri, null);
				return returnCount;

            default:
                return -1;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

}
