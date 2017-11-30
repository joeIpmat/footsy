package com.footsy.footsy;

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
	private FixtureInfo mFixturesInfo;

    public void setFragmentDate(String date) {
        mFragmentDate[0] = date;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        updateScores();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mAdapter = new ScoresAdapter(getActivity(), null, 0);
        final ListView mScoreList = rootView.findViewById(R.id.scoreList);
        mScoreList.setAdapter(mAdapter);

        getLoaderManager().initLoader(SCORES_LOADER, null, this);
        mAdapter.detailMatchId = FixtureList.selectedMatchId;

        mScoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHolder selected = (ViewHolder) view.getTag();
                mAdapter.detailMatchId = selected.matchId;
                FixtureList.selectedMatchId = (int) selected.matchId;
                mAdapter.notifyDataSetChanged();

                //TODO PROBLEM HERE
				mFixturesInfo = new FixtureInfo();
				getFragmentManager().beginTransaction()
						.replace(R.id.container, mFixturesInfo).addToBackStack(null)
						.commit();

                Log.d(TAG, "The match ID is " + mAdapter.detailMatchId);
            }
        });

        return rootView;
    }

    private void updateScores() {
        Intent serviceStart = new Intent(getActivity(), FetchService.class);
        getActivity().startService(serviceStart);
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
