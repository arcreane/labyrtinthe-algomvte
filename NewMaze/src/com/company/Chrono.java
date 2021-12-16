package com.company;

public class Chrono {

    private long tempsDepart = 0;
    private long tempsFin    = 0;
    private long duree       = 0;

    //Fonction qui démarre le timer
    public void start()
    {
        tempsDepart = System.currentTimeMillis();
        tempsFin    = 0;
        duree=0;
    }

    //Fonction qui stop le timer
    public void stop()
    {
        if(tempsDepart == 0) {
            return;
        }
        tempsFin    = System.currentTimeMillis();
        duree       = (tempsFin-tempsDepart);
        tempsDepart = 0;
        tempsFin    = 0;

    }

    //Fonction qui récupère la valeur du timer
    public long getDureeSec()
    {
        return duree / 1000;
    }

    //Fonction qui affiche le résultat du timer
    public static String timeToHMS(long tempsS) {

        // IN : (long) temps en secondes
        // OUT : (String) temps au format texte : "1 h 26 min 3 s"

        //Calcul du format pour convertir en heure, minute et seconde
        int h = (int) (tempsS / 3600);
        int m = (int) ((tempsS % 3600) / 60);
        int s = (int) (tempsS % 60);

        String r="";

        if(h>0) {r+=h+" h ";}
        if(m>0) {r+=m+" min ";}
        if(s>0) {r+=s+" s";}
        if(h<=0 && m<=0 && s<=0) {r="0 s";}

        return r;
    }

}
