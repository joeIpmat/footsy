package com.footsy.footsy;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class FixtureList extends AppCompatActivity {

    private PagerFragment mFixtures;
    public static int selectedMatchId;
    public static int currentFragment = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.actionbar);

        if (savedInstanceState == null) {
            mFixtures = new PagerFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mFixtures)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("Pager_Current", mFixtures.mPagerHandler.getCurrentItem());
        outState.putInt("Selected_match", selectedMatchId);
        getSupportFragmentManager().putFragment(outState, "mFixtures", mFixtures);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        currentFragment = savedInstanceState.getInt("Pager_Current");
        selectedMatchId = savedInstanceState.getInt("Selected_match");
        mFixtures = (PagerFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myMain");
        super.onRestoreInstanceState(savedInstanceState);
    }
}
