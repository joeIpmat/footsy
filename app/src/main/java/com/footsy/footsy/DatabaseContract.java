package com.footsy.footsy;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.footsy.footsy.DatabaseContract.ScoresTable.DATE_COL;

/**
 * Created by Joe on 11/8/17.
 */

public class DatabaseContract {

    public static final String SCORES_TABLE = "ScoresTable";
    public static final String TABLE_HEAD2HEAD = "TableHead2head";

    private static final String CONTENT_AUTHORITY = "com.footsy.footsy";
    public static final String SCORE_PATH = "scores";
	public static final String H2H_PATH = "h2h";
    public static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	public static final class H2hTable implements BaseColumns {
		public static final String MATCH_ID = "fixture_id";
		public static final String MATCH_DATE = "date";
		public static final String HOME_TEAM = "home";
		public static final String AWAY_TEAM = "away";
		public static final String HOME_TEAM_WINS = "home_team_wins";
		public static final String AWAY_TEAM_WINS = "away_team_wins";
		public static final String DRAWS = "draws";
		public static final String GOAL_HOME_TEAM = "goal_home_team";
		public static final String GOAL_AWAY_TEAM = "goal_away_team";

		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_HEAD2HEAD).build();
		public static final String CONTENT_TYPE =
				ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + H2H_PATH;
		public static final String CONTENT_ITEM_TYPE =
				ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + H2H_PATH;

		public static Uri buildH2hWithID() {
			return BASE_CONTENT_URI.buildUpon().appendPath(MATCH_ID).build();
		}
	}

    public static final class ScoresTable implements BaseColumns {
        //Table data
        public static final String LEAGUE_COL = "league";
        public static final String DATE_COL = "date";
        public static final String TIME_COL = "time";
        public static final String HOME_COL = "home";
        public static final String AWAY_COL = "away";
        public static final String HOME_GOALS_COL = "home_goals";
        public static final String AWAY_GOALS_COL = "away_goals";
        public static final String MATCH_ID = "match_id";
        public static final String MATCH_DAY = "match_day";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + SCORE_PATH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + SCORE_PATH;

        public static Uri buildScoreWithDate() {
            return BASE_CONTENT_URI.buildUpon().appendPath(DATE_COL).build();
        }
    }



}
