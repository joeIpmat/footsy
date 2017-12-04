package com.footsy.footsy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class FixtureInfoFragment extends Fragment
		implements LoaderManager.LoaderCallbacks<Cursor> {

	private H2hAdapter mAdapter;
	private static final int H2H_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
		updateH2h();
        View rootView = inflater.inflate(R.layout.fragment_fixture_info, container, false);
		mAdapter = new H2hAdapter(getActivity(), null, 0);
		final ListView mListView = rootView.findViewById(R.id.statistics);
		mListView.setAdapter(mAdapter);

		getLoaderManager().initLoader(H2H_LOADER, null, this);
		mAdapter.detailMatchId = FixtureInfo.selectedMatchId;

		return rootView;
    }

	private void updateH2h() {
		Intent serviceStart = new Intent(getActivity(), FetchH2h.class);
		getActivity().startService(serviceStart);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = DatabaseContract.H2hTable.buildH2hWithID();
		return new CursorLoader(getActivity(), uri, null, null, null, null);
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
