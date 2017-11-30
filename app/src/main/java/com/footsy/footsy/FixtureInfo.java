package com.footsy.footsy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class FixtureInfo extends Fragment
		implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView mListView;
    private TextView mHome;
    private TextView mAway;
    private TextView mResult;
    private FrameLayout mProgressPage;
    private RelativeLayout mHeader;
    private RelativeLayout mErrorPage;
    private RelativeLayout mEmptyPage;
	private H2hAdapter mAdapter;
	private static final int SCORES_LOADER = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

		Intent serviceStart = new Intent(getActivity(), FetchService.class);
		getActivity().startService(serviceStart);

        View view = inflater.inflate(R.layout.fragment_fixture_info, container, false);

		mAdapter = new H2hAdapter(getActivity(), null, 0);
		mListView = view.findViewById(R.id.statistics);
		mListView.setAdapter(mAdapter);

		getLoaderManager().initLoader(SCORES_LOADER, null, this);
//        mHome = view.findViewById(R.id.home);
//        mAway = view.findViewById(R.id.away);
//        mResult = view.findViewById(R.id.result);
//        mProgressPage = view.findViewById(R.id.conprogress);
//        mEmptyPage = view.findViewById(R.id.conempty);
//        mErrorPage = view.findViewById(R.id.conerror);
//        mHeader = view.findViewById(R.id.stats_header);
//        view.setVisibility(View.GONE);

        return view;
    }

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return null;
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
