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
			case "CD Leganes":
				return R.drawable.leganes;
			case "Deportivo Alavés":
				return R.drawable.deportivoalaves;
			case "Valencia CF":
				return R.drawable.valencia;
			case "UD Las Palmas":
				return R.drawable.laspalmas;
			case "RC Celta de Vigo":
				return R.drawable.celtavigo;
			case "Real Sociedad de Fútbol":
				return R.drawable.realsociedad;
			case "Girona FC":
				return R.drawable.girona;
			case "Club Atlético de Madrid":
				return R.drawable.atleticomadrid;
			case "Sevilla FC":
				return R.drawable.sevilla;
			case "RCD Espanyol":
				return R.drawable.espanyol;
			case "Athletic Club":
				return R.drawable.athleticbilbao;
			case "Getafe CF":
				return R.drawable.getafe;
			case "FC Barcelona":
				return R.drawable.fcbarcelona;
			case "Real Betis":
				return R.drawable.realbetis;
			case "RC Deportivo La Coruna":
				return R.drawable.deportivolacoruna;
			case "Real Madrid CF":
				return R.drawable.realmadrid;
			case "Levante UD":
				return R.drawable.levante;
			case "Villarreal CF":
				return R.drawable.villarreal;
			case "Málaga CF":
				return R.drawable.malaga;
			case "SD Eibar":
				return R.drawable.sdeibar;

			//Serie A
			case "Juventus Turin":
				return R.drawable.juventus;
			case "Cagliari Calcio":
				return R.drawable.cagliari;
			case "Hellas Verona FC":
				return R.drawable.hellasverona;
			case "SSC Napoli":
				return R.drawable.napoli;
			case "Atalanta BC":
				return R.drawable.atalanta;
			case "AS Roma":
				return R.drawable.asroma;
			case "Udinese Calcio":
				return R.drawable.udinese;
			case "AC Chievo Verona":
				return R.drawable.chievoverona;
			case "US Sassuolo Calcio":
				return R.drawable.sassuolo;
			case "Genoa CFC":
				return R.drawable.genoa;
			case "UC Sampdoria":
				return R.drawable.sampdoria;
			case "Benevento Calcio":
				return R.drawable.benevento;
			case "SS Lazio":
				return R.drawable.lazio;
			case "SPAL Ferrara":
				return R.drawable.spal;
			case "FC Internazionale Milano":
				return R.drawable.intermilan;
			case "ACF Fiorentina":
				return R.drawable.fiorentina;
			case "Bologna FC":
				return R.drawable.bologna;
			case "Torino FC":
				return R.drawable.torino;
			case "AC Milan":
				return R.drawable.acmilan;

			//Bundesliga
			case "FC Bayern München":
				return R.drawable.bayernmuenchen;
			case "Bayer Leverkusen":
				return R.drawable.bayerleverkusen;
			case "Hamburger SV":
				return R.drawable.hamburgersv;
			case "FC Augsburg":
				return R.drawable.fcaugsburg;
			case "Hertha BSC":
				return R.drawable.herthabsc;
			case "VfB Stuttgart":
				return R.drawable.stuttgart;
			case "TSG 1899 Hoffenheim":
				return R.drawable.hoffenheim;
			case "Werder Bremen":
				return R.drawable.werderbremen;
			case "1. FSV Mainz 05":
				return R.drawable.mainz;
			case "Hannover 96":
				return R.drawable.hannover96;
			case "VfL Wolfsburg":
				return R.drawable.wolfsburg;
			case "Borussia Dortmund":
				return R.drawable.borussiadortmund;
			case "FC Schalke 04":
				return R.drawable.schalke;
			case "SC Freiburg":
				return R.drawable.freiburg;
			case "Eintracht Frankfurt":
				return R.drawable.eintrachtfrankfurt;
			case "Bor. Mönchengladbach":
				return R.drawable.monchengladbach;
			case "1. FC Köln":
				return R.drawable.koln;

			//Ligue 1
			case "AS Monaco FC":
				return R.drawable.asmonaco;
			case "Toulouse FC":
				return R.drawable.toulouse;
			case "Paris Saint-Germain":
				return R.drawable.parisstgermain;
			case "Amiens SC":
				return R.drawable.amienssc;
			case "ES Troyes AC":
				return R.drawable.estactroyes;
			case "Stade Rennais FC":
				return R.drawable.staderennes;
			case "AS Saint-Étienne":
				return R.drawable.assaintetienne;
			case "OGC Nice":
				return R.drawable.ogcnice;
			case "Olympique Lyonnais":
				return R.drawable.olympiquelyon;
			case "RC Strasbourg Alsace":
				return R.drawable.strasbourg;
			case "Montpellier Hérault SC":
				return R.drawable.montpellierhsc;
			case "FC Metz":
				return R.drawable.fcmetz;
			case "EA Guingamp":
				return R.drawable.eaguingamp;
			case "OSC Lille":
				return R.drawable.lilleosc;
			case "FC Nantes":
				return R.drawable.fcnantes;
			case "Angers SCO":
				return R.drawable.angerssco;
			case "FC Girondins de Bordeaux":
				return R.drawable.girondinsbordeaux;
			case "Olympique de Marseille":
				return R.drawable.marseille;
			case "Dijon FCO":
				return R.drawable.dijonfco;

			//Eredivisie
			case "ADO Den Haag":
				return R.drawable.denhaag;
			case "FC Utrecht":
				return R.drawable.utrecht;
			case "PSV Eindhoven":
				return R.drawable.psveindhoven;
			case "AZ Alkmaar":
				return R.drawable.azalkmaar;
			case "VVV Venlo":
				return R.drawable.vvvvenlo;
			case "Sparta Rotterdam":
				return R.drawable.spartarotterdam;
			case "Heracles Almelo":
				return R.drawable.heracles;
			case "Ajax Amsterdam":
				return R.drawable.ajax;
			case "Vitesse Arnhem":
				return R.drawable.vitesse;
			case "NAC Breda":
				return R.drawable.nacbreda;
			case "PEC Zwolle":
				return R.drawable.zwolle;
			case "Roda JC Kerkrade":
				return R.drawable.roda;
			case "Feyenoord Rotterdam":
				return R.drawable.feyenoord;
			case "FC Twente Enschede":
				return R.drawable.twente;
			case "Willem II Tilburg":
				return R.drawable.willem;
			case "Excelsior":
				return R.drawable.excelsior;
			case "FC Groningen":
				return R.drawable.groningen;
			case "SC Heerenveen":
				return R.drawable.heerenveen;

            default:
                return R.drawable.no_icon;
        }
    }
}
