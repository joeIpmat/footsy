package com.footsy.footsy;

import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.button;
import static android.R.id.button1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private TextView title1;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.actionbar);

        findViewsById();

        String[] leagues = getResources().getStringArray(R.array.football_league);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, leagues);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        button1.setOnClickListener(this);
    }

    private void findViewsById() {
        title1 = findViewById(R.id.title1);
        listView = findViewById(R.id.list);
		button1 = findViewById(R.id.button1);
    }

    @Override
    public void onClick(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<>();
        for (int i=0; i<checked.size(); i++) {
            //Item position in adapter
            int position = checked.keyAt(i);
            if(checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }

        String[] outputStr = new String[selectedItems.size()];

        for(int i=0; i<selectedItems.size(); i++) {
            outputStr[i] = selectedItems.get(i);
        }

        Intent intent = new Intent(getApplicationContext(), FixtureList.class);

        Bundle b = new Bundle();
        b.putStringArray("selectedItems", outputStr);

        intent.putExtras(b);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() { exit(); }

    private void exit() {
        AlertDialog.Builder exitAlert = new AlertDialog.Builder(MainActivity.this);
        exitAlert.setTitle("footsy");
        exitAlert.setMessage("Do you want to quit this app?");
        exitAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int w) {
                finish();
            }
        });

        exitAlert.setNegativeButton("No", null);
        exitAlert.show();
    }


}
