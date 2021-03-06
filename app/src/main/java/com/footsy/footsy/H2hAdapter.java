package com.footsy.footsy;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by Joe on 11/28/17.
 */

public class H2hAdapter extends CursorAdapter {

	public static final int COL_ID = 8;
	public double detailMatchId = 0;

	public H2hAdapter(Context context, Cursor cursor, int flags) {
		super(context, cursor, flags);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View mItem = LayoutInflater.from(context).inflate(R.layout.h2h_list_item, parent, false);
		final H2HViewHolder mHolder = new H2HViewHolder(mItem);
		mItem.setTag(mHolder);
		return mItem;
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		final H2HViewHolder mHolder = (H2HViewHolder) view.getTag();
//		mHolder.homeTeamName.setText(cursor.getString(COL_HOME));
//		mHolder.awayTeamName.setText(cursor.getString(COL_AWAY));
		int homeIndex = cursor.getColumnIndex(DatabaseContract.H2hTable.HOME_TEAM);
		int awayIndex = cursor.getColumnIndex(DatabaseContract.H2hTable.AWAY_TEAM);
		int scoreAwayIndex = cursor.getColumnIndex(DatabaseContract.H2hTable.GOAL_AWAY_TEAM);
		int scoreHomeIndex = cursor.getColumnIndex(DatabaseContract.H2hTable.GOAL_HOME_TEAM);
		mHolder.homeTeamName.setText(cursor.getString(homeIndex));
		mHolder.awayTeamName.setText(cursor.getString(awayIndex));
		mHolder.score.setText(Utility.getScores(cursor.getInt(scoreHomeIndex),
				cursor.getInt(scoreAwayIndex)));
		mHolder.matchId = cursor.getDouble(COL_ID);
	}
}
