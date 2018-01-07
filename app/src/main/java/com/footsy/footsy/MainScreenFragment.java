package com.footsy.footsy;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Joe on 11/8/17.
 */

public class MainScreenFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static String TAG = MainScreenFragment.class.getSimpleName();
    private final String[] mFragmentDate = new String[1];
    private ScoresAdapter mAdapter;
    private static final int SCORES_LOADER = 0;
	private static String matchIDclick = "";

	public View noMatch;
    public void setFragmentDate(String date) {
        mFragmentDate[0] = date;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        updateScores();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mAdapter = new ScoresAdapter(getActivity(), null, 0);
		noMatch = rootView.findViewById(R.id.conempty);

		final ListView mScoreList = rootView.findViewById(R.id.scoreList);
		mScoreList.setAdapter(mAdapter);
		mScoreList.setEmptyView(noMatch);

		getLoaderManager().initLoader(SCORES_LOADER, null, this);
		mAdapter.detailMatchId = FixtureList.selectedMatchId;

		mScoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				/** get the matchID **/
				Cursor curr = (Cursor) mScoreList.getItemAtPosition(position);
				matchIDclick = curr.getString(8).substring(1);
				//Log.d("Position:", Integer.toString(position) + "Cursor: " + name);
				/** EUREKA FINALLY MADE IT **/
				DBHelper db = new DBHelper(getContext());
				db.deleteAll();
				Intent intent = new Intent(getActivity(), FixtureInfo.class);
				startActivity(intent);
			}
		});


        return rootView;
    }

    private void updateScores() {
        Intent serviceStart = new Intent(getActivity(), FetchService.class);
        getActivity().startService(serviceStart);
    }

	public static String getClickedID() {
		return matchIDclick;
	}

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), DatabaseContract.ScoresTable.buildScoreWithDate(),
                null, null, mFragmentDate, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cursor.moveToNext();
        }
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }
}
