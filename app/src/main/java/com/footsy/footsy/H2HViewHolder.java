package com.footsy.footsy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Joe on 11/10/17.
 */

public class H2HViewHolder {

	public TextView homeTeamName;
	public TextView awayTeamName;
	public TextView score;
	public TextView date;
	public ImageView homeCrest;
	public ImageView awayCrest;
	public double matchId;

	public H2HViewHolder(View view) {
		homeTeamName = view.findViewById(R.id.home_name_h2h);
		awayTeamName = view.findViewById(R.id.away_name_h2h);
		score = view.findViewById(R.id.score_textview_h2h);
	}

}
