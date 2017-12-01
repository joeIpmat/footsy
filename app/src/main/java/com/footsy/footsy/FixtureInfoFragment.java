package com.footsy.footsy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class FixtureInfoFragment extends Fragment
		implements LoaderManager.LoaderCallbacks<Cursor> {

	private final String mID = new String();
    private FrameLayout mProgressPage;
    private RelativeLayout mErrorPage;
    private RelativeLayout mEmptyPage;
	private H2hAdapter mAdapter;
	private static final int H2H_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

		Intent serviceStart = new Intent(getActivity(), FetchService.class);
		getActivity().startService(serviceStart);

        View rootView = inflater.inflate(R.layout.fragment_fixture_info, container, false);

		mAdapter = new H2hAdapter(getActivity(), null, 0);
		final ListView mListView = rootView.findViewById(R.id.statistics);
		mListView.setAdapter(mAdapter);

        mProgressPage = rootView.findViewById(R.id.conprogress);
		mProgressPage.setVisibility(View.GONE);
        mEmptyPage = rootView.findViewById(R.id.conempty);
		mEmptyPage.setVisibility(View.GONE);
        mErrorPage = rootView.findViewById(R.id.conerror);
		mErrorPage.setVisibility(View.GONE);

		getLoaderManager().initLoader(H2H_LOADER, null, this);
		mAdapter.detailMatchId = FixtureInfo.selectedMatchId;

        return rootView;
    }

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    	Uri uri = DatabaseContract.H2hTable.buildH2hWithID();
		return new CursorLoader(getActivity(), uri,
				null, null, null, null);
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
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
