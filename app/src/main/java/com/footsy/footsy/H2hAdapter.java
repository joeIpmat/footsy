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

	public static final int COL_HOME = 3;
	public static final int COL_AWAY = 4;
	public static final int COL_HOME_GOALS = 6;
	public static final int COL_AWAY_GOALS = 7;
	public static final int COL_ID = 8;
	public double detailMatchId = 0;

	public H2hAdapter(Context context, Cursor cursor, int flags) {
		super(context, cursor, flags);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View mItem = LayoutInflater.from(context).inflate(R.layout.h2h_list_item, parent, false);
		final ViewHolder mHolder = new ViewHolder(mItem);
		mItem.setTag(mHolder);
		return mItem;
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		final ViewHolder mHolder = (ViewHolder) view.getTag();
		mHolder.homeTeamName.setText(cursor.getString(COL_HOME));
		mHolder.awayTeamName.setText(cursor.getString(COL_AWAY));
		mHolder.score.setText(Utility.getScores(cursor.getInt(COL_HOME_GOALS), cursor.getInt(COL_AWAY_GOALS)));
		mHolder.matchId = cursor.getDouble(COL_ID);
	}
}
