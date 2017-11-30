package com.footsy.footsy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * Created by Joe on 11/9/17.
 */

public class ScoresAdapter extends CursorAdapter {

    public static final int COL_HOME = 3;
    public static final int COL_AWAY = 4;
    public static final int COL_HOME_GOALS = 6;
    public static final int COL_AWAY_GOALS = 7;
    public static final int COL_DATE = 1;
    public static final int COL_LEAGUE = 5;
    public static final int COL_MATCHDAY = 9;
    public static final int COL_ID = 8;
    public static final int COL_MATCHTIME = 2;
    public double detailMatchId = 0;

    public ScoresAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View mItem = LayoutInflater.from(context).inflate(R.layout.scores_list_item, parent, false);
        final ViewHolder mHolder = new ViewHolder(mItem);
        mItem.setTag(mHolder);
        return mItem;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final ViewHolder mHolder = (ViewHolder) view.getTag();
        mHolder.homeTeamName.setText(cursor.getString(COL_HOME));
        mHolder.awayTeamName.setText(cursor.getString(COL_AWAY));
        mHolder.date.setText(cursor.getString(COL_MATCHTIME));
        mHolder.score.setText(Utility.getScores(cursor.getInt(COL_HOME_GOALS), cursor.getInt(COL_AWAY_GOALS)));
        mHolder.matchId = cursor.getDouble(COL_ID);
        mHolder.homeCrest.setImageResource(Utility.getTeamCrestByTeamName(cursor.getString(COL_HOME)));
        mHolder.awayCrest.setImageResource(Utility.getTeamCrestByTeamName(cursor.getString(COL_AWAY)
        ));

        LayoutInflater vi = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.detail_fragment, null);
        ViewGroup container = view.findViewById(R.id.details_fragment_container);

        if (mHolder.matchId == detailMatchId) {
            container.removeAllViews();
            container.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            TextView match_day = v.findViewById(R.id.matchday_textview);
            match_day.setText(Utility.getMatchDay(cursor.getInt(COL_MATCHDAY), cursor.getInt(COL_LEAGUE)));

            TextView league = v.findViewById(R.id.league_textview);
            league.setText(Utility.getLeague(cursor.getInt(COL_LEAGUE)));

            Button share_button = v.findViewById(R.id.share_button);
            /** share doesnt need to be unique, thats why its here **/
            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add Share Action
                    context.startActivity(createShareForecastIntent(mHolder.homeTeamName.getText() + " "
                            + mHolder.score.getText() + " " + mHolder.awayTeamName.getText() + " "));
                }
            });
        } else {
            container.removeAllViews();
        }
    }

    public Intent createShareForecastIntent(String ShareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, ShareText + "footy");
        return shareIntent;
    }

}