package com.footsy.footsy;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import static android.os.Build.ID;

/**
 * Created by Joe on 12/3/17.
 */

public class FetchH2h extends IntentService {

	private final static String TAG = FetchH2h.class.getSimpleName();
	private final String BASE_URL = "http://api.football-data.org/v1/fixtures";
	String matchID = "";

	public FetchH2h() {
		super("FetchH2h");
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {
		getHead2Head(3); //last 3 meetings
	}

	private void getHead2Head(int count) {
		final String QUERY_H2H = "head2head";
		matchID = MainScreenFragment.getClickedID();
		//Log.d("matchID", matchID);

		Uri fetchBuilder = Uri.parse(BASE_URL).buildUpon().
				appendPath(matchID).
				appendQueryParameter(QUERY_H2H, Integer.toString(count)).build();
		Log.v(TAG, "The url for head2head we looking at is: " + fetchBuilder.toString());

		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String JSONdata = null;

		try {
			/** Start Connection **/
			URL fetch = new URL(fetchBuilder.toString());
			conn = (HttpURLConnection) fetch.openConnection();

			conn.setInstanceFollowRedirects(true);
			conn.setUseCaches(true);
			conn.setDefaultUseCaches(true);

			conn.setRequestMethod("GET");
			conn.addRequestProperty("X-Auth-Token", getString(R.string.api_key));

			boolean redirect = false;
			int status = conn.getResponseCode();
			if(status != HttpURLConnection.HTTP_OK) {
				if(status == HttpURLConnection.HTTP_MOVED_TEMP
						|| status == HttpURLConnection.HTTP_MOVED_PERM
						|| status == HttpURLConnection.HTTP_SEE_OTHER)
					redirect = true;
			}
			Log.d(TAG, "===> HTTP STATUS CODE: " + status + ", redirect=" + redirect);
			conn.connect();

			/** Read Input **/
			InputStream inStream = conn.getInputStream();
			if (inStream == null) {
				return;
			}
			reader = new BufferedReader(new InputStreamReader(inStream));

			StringBuilder buffer = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			//empty Stream, no parsing needed
			if(buffer.length() == 0) {
				return;
			}
			JSONdata = buffer.toString();
		}
		catch (Exception e) {
			Log.e(TAG, "Exception here" + e.getMessage());
		}
		finally {
			if (conn != null) {
				conn.disconnect();
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.e(TAG, "Error Closing Stream");
				}
			}
		}
		/** get head2head from JSONdata **/
		try {
			if (JSONdata != null) {
				JSONObject matches = new JSONObject(JSONdata)
						.getJSONObject("head2head");
//                if (matches.length() == 0) {
//                    return;
//                }
				//if there are matches, process data & show
				processJSONhead2head(JSONdata, getApplicationContext(), true);
			} else {
				Log.d(TAG, "Can't connect to server!");
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private void processJSONhead2head(String jsonData, Context mContext, boolean isReal) {

		final String HEAD2HEAD = "head2head";
		final String FIXTURES = "fixtures";
		final String HOMEWINS = "homeTeamWins";
		final String AWAYWINS = "awayTeamWins";
		final String DRAWS = "draws";
		final String HOME_TEAM = "homeTeamName";
		final String AWAY_TEAM = "awayTeamName";
		final String RESULT = "result";
		final String HOME_GOALS = "goalsHomeTeam";
		final String AWAY_GOALS = "goalsAwayTeam";
		final String DATE = "date";

		String mHome;
		String mAway;
		String mMatchDate;
		String mHomeTeamwins;
		String mAwayTeamWins;
		String mDraws;
		String mGoalsHomeTeam;
		String mGoalsAwayTeam;
		ContentValues h2hValues = new ContentValues();

		/** head to head **/
		try {
			JSONObject h2hData = new JSONObject(jsonData).getJSONObject(HEAD2HEAD);
			JSONObject currentMatch = new JSONObject(jsonData).getJSONObject("fixture");
			JSONArray h2hMatches = new JSONObject(jsonData).getJSONObject("head2head").getJSONArray(FIXTURES);
			Vector<ContentValues> values = new Vector<>(h2hMatches.length());

			mHomeTeamwins = h2hData.optString(HOMEWINS);
			mAwayTeamWins = h2hData.optString(AWAYWINS);
			mDraws = h2hData.optString(DRAWS);

			mMatchDate = currentMatch.getString(DATE);
			mMatchDate = mMatchDate.substring(0, mMatchDate.indexOf("T"));

			h2hValues.put(DatabaseContract.H2hTable.MATCH_ID, matchID);
			h2hValues.put(DatabaseContract.H2hTable.HOME_TEAM_WINS, mHomeTeamwins);
			h2hValues.put(DatabaseContract.H2hTable.AWAY_TEAM_WINS, mAwayTeamWins);
			h2hValues.put(DatabaseContract.H2hTable.DRAWS, mDraws);
			h2hValues.put(DatabaseContract.H2hTable.MATCH_DATE, mMatchDate);

			for(int i = 0; i < h2hMatches.length(); i++) {
				JSONObject match_data = h2hMatches.getJSONObject(i);
				mHome = match_data.getString(HOME_TEAM);
				mAway = match_data.getString(AWAY_TEAM);
				mGoalsAwayTeam = match_data.getJSONObject(RESULT).getString(AWAY_GOALS);
				mGoalsHomeTeam = match_data.getJSONObject(RESULT).getString(HOME_GOALS);

				h2hValues.put(DatabaseContract.H2hTable.HOME_TEAM, mHome);
				h2hValues.put(DatabaseContract.H2hTable.AWAY_TEAM, mAway);
				h2hValues.put(DatabaseContract.H2hTable.GOAL_AWAY_TEAM, mGoalsAwayTeam);
				h2hValues.put(DatabaseContract.H2hTable.GOAL_HOME_TEAM, mGoalsHomeTeam);

				values.add(h2hValues);
			}
			int inserted_data;
			ContentValues[] insertData = new ContentValues[values.size()];
			values.toArray(insertData);
			inserted_data = mContext.getContentResolver()
					.bulkInsert(DatabaseContract.H2hTable.buildH2hWithID(), insertData);
			Log.v(TAG,"Succesfully Inserted h2h : " + String.valueOf(inserted_data));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
