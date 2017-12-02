package com.footsy.footsy;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import static android.R.attr.id;


/**
 * Created by Joe on 11/8/17.
 */

public class FetchService extends IntentService {

    private final static String TAG = FetchService.class.getSimpleName();
    private final String BASE_URL = "http://api.football-data.org/v1/fixtures";
	String matchID;

    public FetchService() {
        super("FetchService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getFixture("n14"); //next 14 days
        getFixture("p14"); //previous 14 days TODO: missing previous days!
		getHead2Head(3); //last 3 meetings
    }

    //TODO All these need refactor!

    private void getHead2Head(int count) {
		final String QUERY_H2H = "head2head";
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

    /** Fixture timeframe **/
    private void getFixture(String timeFrame) {
        final String QUERY_TIMEFRAME = "timeFrame";
        Uri fetchBuilder = Uri.parse(BASE_URL).buildUpon().
                appendQueryParameter(QUERY_TIMEFRAME, timeFrame).build();
        Log.v(TAG, "The url for matches we looking at is: "+fetchBuilder.toString());
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
        /** get FIXTURES from JSONdata **/
        try {
            if (JSONdata != null) {
                JSONArray matches = new JSONObject(JSONdata)
                        .getJSONArray("fixtures");
                if (matches.length() == 0) {
                    //no match so we return nothing, probably off season
                    return;
                }
                //if there are matches, process data & show
                processFixture(JSONdata, getApplicationContext(), true);
            } else {
                Log.d(TAG, "Can't connect to server!");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /** process data and show in view **/

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

    private void processFixture(String jsonData, Context mContext, boolean isReal) {
        //JSON data
        final String BUNDESLIGA = "452";
        final String LIGUE1 = "450";
        final String PREMIER_LEAGUE = "445";
        final String SERIE_A = "456";
        final String EREDIVISIE = "449";
        final String PRIMERA_DIVISION = "455";

        final String SEASON_LINK = "http://api.football-data.org/v1/competitions/";
        final String MATCH_LINK = "http://api.football-data.org/v1/fixtures";
        final String LINKS = "_links";
        final String SOCCER_SEASON = "competition";
        final String SELF = "self";
        final String FIXTURES = "fixtures";
        final String MATCH_DATE = "date";
        final String HOME_TEAM = "homeTeamName";
        final String AWAY_TEAM = "awayTeamName";
        final String RESULT = "result";
        final String HOME_GOALS = "goalsHomeTeam";
        final String AWAY_GOALS = "goalsAwayTeam";
        final String MATCHDAY = "matchday";

        String league;
        String mDate;
        String mTime;
        String home;
        String away;
        String homeGoals;
        String awayGoals;
        String matchDay;

        /** match list **/
        try {
            /** Populate array with matches **/
            JSONArray matches = new JSONObject(jsonData).getJSONArray(FIXTURES);

            Vector<ContentValues> values = new Vector<>(matches.length());

            /** loop through fixture array **/
            for(int i = 0; i < matches.length(); i++) {

                JSONObject match_data = matches.getJSONObject(i);
                league = match_data.getJSONObject(LINKS).getJSONObject(SOCCER_SEASON).
                        getString("href");
                league = league.replace(SEASON_LINK, ""); //remove the link, get league code

                if (league.equals(PREMIER_LEAGUE) ||
                        league.equals(SERIE_A) ||
                        league.equals(BUNDESLIGA) ||
                        league.equals(PRIMERA_DIVISION) ||
                        league.equals(LIGUE1) ||
                        league.equals(EREDIVISIE)) {

                    matchID = match_data.getJSONObject(LINKS).getJSONObject(SELF).getString("href");
                    matchID = matchID.replace(MATCH_LINK, "");

//                    if(!isReal) {
//                        matchID = matchID + Integer.toString(i);
//                    }

                    mDate = match_data.getString(MATCH_DATE);
                    mTime = mDate.substring(mDate.indexOf("T") + 1, mDate.indexOf("Z"));
                    mDate = mDate.substring(0, mDate.indexOf("T"));
                    SimpleDateFormat matchDate = new SimpleDateFormat ("yyyy-MM-ddHH:mm:ss");
                    matchDate.setTimeZone(TimeZone.getTimeZone("UTC"));

                    try {
                        Date parseDate = matchDate.parse(mDate + mTime);
                        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
                        newDate.setTimeZone(TimeZone.getDefault());
                        mDate = newDate.format(parseDate);
                        mTime = mDate.substring(mDate.indexOf(":") + 1);
                        mDate = mDate.substring(0, mDate.indexOf(":"));

//                        if(!isReal) {
//                            Date fragmentDate = new Date(System.currentTimeMillis() + ((i-2) * 86400000));
//                            SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
//                            mDate = mFormat.format(fragmentDate);
//                        }
                    }
                    catch (Exception e ) {
                        Log.d("FetchService", "<- error here!");
                        Log.e("FetchService", e.getMessage());
                    }

                    home = match_data.getString(HOME_TEAM);
                    away = match_data.getString(AWAY_TEAM);
                    homeGoals = match_data.getJSONObject(RESULT).getString(HOME_GOALS);
                    awayGoals = match_data.getJSONObject(RESULT).getString(AWAY_GOALS);
                    matchDay = match_data.getString(MATCHDAY);

                    ContentValues matchValues = new ContentValues();
                    matchValues.put(DatabaseContract.ScoresTable.MATCH_ID, matchID);
                    matchValues.put(DatabaseContract.ScoresTable.DATE_COL, mDate);
                    matchValues.put(DatabaseContract.ScoresTable.TIME_COL, mTime);
                    matchValues.put(DatabaseContract.ScoresTable.HOME_COL, home);
                    matchValues.put(DatabaseContract.ScoresTable.AWAY_COL, away);
                    matchValues.put(DatabaseContract.ScoresTable.HOME_GOALS_COL, homeGoals);
                    matchValues.put(DatabaseContract.ScoresTable.AWAY_GOALS_COL, awayGoals);
                    matchValues.put(DatabaseContract.ScoresTable.LEAGUE_COL, league);
                    matchValues.put(DatabaseContract.ScoresTable.MATCH_DAY, matchDay);

                    values.add(matchValues);
                }
            }
            int inserted_data;
            ContentValues[] insertData = new ContentValues[values.size()];
            values.toArray(insertData);
            inserted_data = mContext.getContentResolver()
                    .bulkInsert(DatabaseContract.BASE_CONTENT_URI, insertData);
            Log.v(TAG,"Succesfully Inserted : " + String.valueOf(inserted_data));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}