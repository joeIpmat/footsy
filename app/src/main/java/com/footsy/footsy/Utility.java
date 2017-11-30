package com.footsy.footsy;

import android.content.Context;

/**
 * Created by Joe on 11/10/17.
 */

public class Utility {

    public static final int SERIE_A = 456;
    public static final int PREMIER_LEAGUE = 445;
    public static final int PRIMERA_DIVISION = 455;
    public static final int BUNDESLIGA = 452;
    public static final int LIGUE1 = 450;
    public static final int EREDIVISIE = 449;

    public static String getLeague(int league_num) {
        switch (league_num) {
            case SERIE_A:
                return "Seria A";
            case PREMIER_LEAGUE:
                return "Premier League";
            case PRIMERA_DIVISION:
                return "Primera Division";
            case BUNDESLIGA:
                return "Bundesliga";
            case LIGUE1:
                return "Ligue 1";
            case EREDIVISIE:
                return "Eredivisie";
            default:
                return "Not known League Please report";
        }
    }

    public static String getMatchDay(int match_day, int league_num) {
        return "Matchday : " + String.valueOf(match_day);
    }

    public static String getScores(int home_goals, int awaygoals) {
        if (home_goals < 0 || awaygoals < 0) {
            return " - ";
        } else {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName(String teamname) {
        if (teamname == null) {
            return R.drawable.no_icon;
        }
        switch (teamname) {
			//EPL
            case "Arsenal FC":
                return R.drawable.arsenal;
            case "Manchester United FC":
                return R.drawable.manunited;
            case "Swansea City FC":
                return R.drawable.swansea;
            case "Leicester City FC":
                return R.drawable.leicester;
            case "Everton FC":
                return R.drawable.everton;
            case "West Ham United FC":
                return R.drawable.westham;
            case "Tottenham Hotspur FC":
                return R.drawable.tottenham;
            case "West Bromwich Albion":
                return R.drawable.westbrom;
            case "Stoke City FC":
                return R.drawable.stoke;
			case "Watford FC":
				return R.drawable.watford;
			case "Liverpool FC":
				return R.drawable.liverpool;
			case "Southampton FC":
				return R.drawable.southampton;
			case "West Bromwich Albion FC":
				return R.drawable.westbrom;
			case "AFC Bournemouth":
				return R.drawable.bournemouth;
			case "Crystal Palace FC":
				return R.drawable.crystalpalace;
			case "Huddersfield Town":
				return R.drawable.huddersfield;
			case "Chelsea FC":
				return R.drawable.chelsea;
			case "Brighton & Hove Albion":
				return R.drawable.brighton;
			case "Manchester City FC":
				return R.drawable.mancity;
			case "Newcastle United FC":
				return R.drawable.newcastle;

			//LaLiga

            default:
                return R.drawable.no_icon;
        }
    }
}
