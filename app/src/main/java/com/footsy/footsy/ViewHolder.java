package com.footsy.footsy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Joe on 11/10/17.
 */

public class ViewHolder {

    public TextView homeTeamName;
    public TextView awayTeamName;
    public TextView score;
    public TextView date;
    public ImageView homeCrest;
    public ImageView awayCrest;
    public double matchId;

    public ViewHolder(View view) {
        homeTeamName = view.findViewById(R.id.home_name);
        awayTeamName = view.findViewById(R.id.away_name);
        score = view.findViewById(R.id.score_textview);
        date = view.findViewById(R.id.date_textview);
        homeCrest = view.findViewById(R.id.home_crest);
        awayCrest = view.findViewById(R.id.away_crest);
    }

}
