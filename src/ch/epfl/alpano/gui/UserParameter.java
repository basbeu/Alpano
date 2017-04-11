package ch.epfl.alpano.gui;

/**
 * Enumération représentant les différents paramêtres saisis par l'utilisateurs
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public enum UserParameter {
    /**
     * Longitude de l'observateur comprise entre 6° et 12°
     */
    OBSERVER_LONGITUDE(60_000, 120_000),
    /**
     * Latitude de l'observateur comprise entre 45° et 48°
     */
    OBSERVER_LATITUDE(450_000, 480_000),
    /**
     * Altitude de l'observateur comprise entre 300m et 10000m
     */
    OBSERVER_ELEVATION(300, 10_000),
    /**
     * Azimuth central compris entre 0° et 359°
     */
    CENTER_AZIMUTH(0, 359),
    /**
     * Champ de vision horizontal compris entre 1° et 360°
     */
    HORIZONTAL_FIELD_OF_VIEW(1, 360),
    /**
     * Distance maximale comprise entre 10km et 600km
     */
    MAX_DISTANCE(10, 600),
    /**
     * Largeur de l'image comprise entre 30 et 16000 echantillons
     */
    WIDTH(30, 16_000),
    /**
     * Hauteur de l'image comprise entre 10 et 4000 echantillons
     */
    HEIGHT(10, 4_000),
    /**
     * Exposant de surechantillonage compris entre 0 et 2
     */
    SUPER_SAMPLING_EXPONENT(0, 2);
    
    private int min;
    private int max;
    
    /**
     * Constructeur d'un parametre d'utilisateur
     * @param min int borne minimale de la valeur d'un parametre
     * @param max int borne maximale de la valeur d'un parametre
     */
    private UserParameter(int min, int max){
        this.min = min;
        this.max = max;
    }
    
    /**
     * Methode validant la valeur d'un parametre
     * @param value int representant valeur du parametre a verifier
     * @return int representant la valeur etant valide la plus proche
     */
    public int sanitize(int value){
        if(value<min)
            return min;
        else if(value>max)
            return max;
        else
            return value;
    }
}
