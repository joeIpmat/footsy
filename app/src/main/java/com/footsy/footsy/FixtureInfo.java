package com.footsy.footsy;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Joe on 11/30/17.
 */

public class FixtureInfo extends AppCompatActivity {

	private FixtureInfoFragment mFixtureInfo;
	public static int selectedMatchId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fixtures);

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.actionbar);

		if (savedInstanceState == null) {
			mFixtureInfo = new FixtureInfoFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.containerMain, mFixtureInfo)
					.commit();
		}
	}

}
