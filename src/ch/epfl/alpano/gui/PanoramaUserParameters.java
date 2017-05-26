package ch.epfl.alpano.gui;

import static java.lang.Math.toRadians;
import static java.lang.Math.pow;
import static ch.epfl.alpano.Preconditions.checkArgument;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.PanoramaParameters;

/**
 * Classe public et immuable represente les parametres d'un panorama d'un point de vue de l'utilisateur final de l'application
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public final class PanoramaUserParameters {

    private final static int TO_KM = 1_000;
    private final static double TO_TENTHOUSANDS_RADIAN = 10_000;
    private final static int MAX_VERTICAL_FIELD_OF_VIEW = 170;

    private Map<UserParameter, Integer> userParameters;

    /**
     * Constructeur principal des parametre d'un panorama
     * @param userParameters Map avec comme cle des parametres de l'utilisateur et comme valeur assicee des Integer
     * @throws IllegalArgumentException si la map ne contient pas tous les parametres
     */
    public PanoramaUserParameters(Map<UserParameter, Integer> userParameters){
        for(UserParameter param : UserParameter.values())
            checkArgument(userParameters.containsKey(param), "La Map doit contenir tous les paramètres");

        //controle de la hauteur
        int height = UserParameter.HEIGHT.sanitize(userParameters.get(UserParameter.HEIGHT));
        int width =  UserParameter.WIDTH.sanitize(userParameters.get(UserParameter.WIDTH));
        int vh = UserParameter.HORIZONTAL_FIELD_OF_VIEW.sanitize(userParameters.get(UserParameter.HORIZONTAL_FIELD_OF_VIEW));

        if(((height - 1) / (width - 1)) * vh > MAX_VERTICAL_FIELD_OF_VIEW)
            height = (MAX_VERTICAL_FIELD_OF_VIEW * (width - 1)) / vh;

        Map<UserParameter, Integer> up = map(
                UserParameter.OBSERVER_LONGITUDE.sanitize(userParameters.get(UserParameter.OBSERVER_LONGITUDE)),
                UserParameter.OBSERVER_LATITUDE.sanitize(userParameters.get(UserParameter.OBSERVER_LATITUDE)),
                UserParameter.OBSERVER_ELEVATION.sanitize(userParameters.get(UserParameter.OBSERVER_ELEVATION)),
                UserParameter.CENTER_AZIMUTH.sanitize(userParameters.get(UserParameter.CENTER_AZIMUTH)),
                vh,
                UserParameter.MAX_DISTANCE.sanitize(userParameters.get(UserParameter.MAX_DISTANCE)),
                width,
                height,
                UserParameter.SUPER_SAMPLING_EXPONENT.sanitize(userParameters.get(UserParameter.SUPER_SAMPLING_EXPONENT)));        
        this.userParameters = Collections.unmodifiableMap(new EnumMap<>(up));
    }
    
    /**
     * Méthode privee creant un panoramaParameters pour un  exposant de super-echantillonage donne
     * @param superSamplingExponent int Exposant de super-echantillonnage
     * @return PanoramaParameters correspondant a l'exposant de super-echantillonage
     */
    private PanoramaParameters buildPanoramaParameters(int superSamplingExponent){
        int wp = (int)pow(2, superSamplingExponent) * width();
        int hp = (int)pow(2, superSamplingExponent) * height();

        return new PanoramaParameters(observerPosition(), observerElevation(), toRadians(centerAzimuth()), toRadians(horizontalFieldOfView()), maxDistance() * TO_KM, wp, hp);
    }

    /**
     * Constructeur secondaire 
     * @param observerLongitude int longitude de l'observateur
     * @param observerLatitude int latitude de l'observateur
     * @param observerElevation altitude de l'observateur
     * @param centerAzimuth int azimute central
     * @param horizontalFieldOfView int angle horizonal de vue
     * @param maxDistance int distance de vue maximale
     * @param width int largeur du panorama
     * @param height int hauteur du panorama
     * @param superSamplingExponent int facteur de surechantillonage
     */
    public PanoramaUserParameters(int observerLongitude, int observerLatitude, int observerElevation, int centerAzimuth, int horizontalFieldOfView, int maxDistance, int width, int height, int superSamplingExponent){
        this(map(observerLongitude, observerLatitude, observerElevation, centerAzimuth, horizontalFieldOfView, maxDistance, width, height, superSamplingExponent));
    }

    /**
     * Methode privee pour construire une map
     * @param observerLongitude int longitude de l'observateur
     * @param observerLatitude int latitude de l'observateur
     * @param observerElevation altitude de l'observateur
     * @param centerAzimuth int azimute central
     * @param horizontalFieldOfView int angle horizonal de vue
     * @param maxDistance int distance de vue maximale
     * @param width int largeur du panorama
     * @param height int hauteur du panorama
     * @param superSamplingExponent int facteur de surechantillonage
     * @return Map les parametres de l'utilisateur associes a leur valeur en int
     */
    private static Map<UserParameter, Integer> map(int observerLongitude, int observerLatitude, int observerElevation, int centerAzimuth, int horizontalFieldOfView, int maxDistance, int width, int height, int superSamplingExponent){
        Map<UserParameter, Integer> userParam = new EnumMap<>(UserParameter.class);
        userParam.put(UserParameter.OBSERVER_LONGITUDE, observerLongitude);
        userParam.put(UserParameter.OBSERVER_LATITUDE, observerLatitude);
        userParam.put(UserParameter.OBSERVER_ELEVATION, observerElevation);
        userParam.put(UserParameter.CENTER_AZIMUTH, centerAzimuth);
        userParam.put(UserParameter.HORIZONTAL_FIELD_OF_VIEW, horizontalFieldOfView);
        userParam.put(UserParameter.MAX_DISTANCE, maxDistance);
        userParam.put(UserParameter.WIDTH, width);
        userParam.put(UserParameter.HEIGHT, height);
        userParam.put(UserParameter.SUPER_SAMPLING_EXPONENT, superSamplingExponent);
        return userParam;
    }

    /**
     * Methode privee pour convertir les degres en radian a l'echelle
     * @param degree int degre a convertir
     * @return double la valeur en radian
     */
    private static double tenThousandthDegreesToRadian(int degree){
        return toRadians(degree / TO_TENTHOUSANDS_RADIAN);
    }

    /**
     * Methode privee pour calculer le GeoPoint de la position de l'observateur
     * @return GeoPoint coordonnee de l'observateur
     */
    private GeoPoint observerPosition(){
        return new GeoPoint(tenThousandthDegreesToRadian(observerLongitude()), tenThousandthDegreesToRadian(observerLatitude())); 
    }

    /**
     * Accesseur public de la valeur associees au parametre up
     * @param up Userparameter parametre de l'utilisateur
     * @return int valeur associee a ce parametre
     */
    public int get(UserParameter up){
        return userParameters.get(up);
    }

    /**
     * Accesseur public de la longitude de l'observateur
     * @return int longitude de l'observateur
     */
    public int observerLongitude(){
        return userParameters.get(UserParameter.OBSERVER_LONGITUDE);
    }

    /**
     * Accesseur public de la latitude de l'observateur
     * @return int latitude de l'observateur
     */
    public int observerLatitude(){
        return userParameters.get(UserParameter.OBSERVER_LATITUDE);
    }

    /**
     * Accesseur public de l'altitude de l'observateur
     * @return int altitude de l'observateur
     */
    public int observerElevation(){
        return userParameters.get(UserParameter.OBSERVER_ELEVATION);
    }

    /**
     * Accesseur public de l'azimute central
     * @return int valeur de l'azimute central
     */
    public int centerAzimuth(){
        return userParameters.get(UserParameter.CENTER_AZIMUTH);
    }

    /**
     * Accesseur public de l'angle de vue horizontal
     * @return int angle de vue horizontal
     */
    public int horizontalFieldOfView(){
        return userParameters.get(UserParameter.HORIZONTAL_FIELD_OF_VIEW);
    }

    /**
     * Accesseur public de la distance maximale de vue
     * @return int distance maximale de vue
     */
    public int maxDistance(){
        return userParameters.get(UserParameter.MAX_DISTANCE);
    }

    /**
     * Accesseur public de la largeur du panorama
     * @return int largeur de panorama
     */
    public int width(){
        return userParameters.get(UserParameter.WIDTH);
    }

    /**
     * Accesseur public de la hauteur du panorama 
     * @return int hauteur du panorama
     */
    public int height(){
        return userParameters.get(UserParameter.HEIGHT);
    }

    /**
     * Accesseur public de l'exposant de surechantillonnage
     * @return int exposant de surechantillonnage
     */
    public int superSamplingExponent(){
        return userParameters.get(UserParameter.SUPER_SAMPLING_EXPONENT);
    }

    /**
     * Methode public retournant les parametres du panorama tel qu'il sera calcule
     * @return PanoramaParameters parametres du panorama tel qu'il sera calcule
     */
    public PanoramaParameters panoramaParameters(){
        return buildPanoramaParameters(superSamplingExponent());
    }

    /**
     * Methode public retournant les parametres du panorama tel qu'il sera affiche
     * @return PanoramaParameters parametres du panorama tel qu'il sera affiche
     */
    public PanoramaParameters panoramaDisplayParameters(){
        return buildPanoramaParameters(0);
    }

    @Override
    public boolean equals(Object t){
        return (t instanceof PanoramaUserParameters) && (((PanoramaUserParameters)t).userParameters.equals(userParameters));
    }

    @Override
    public int hashCode(){
        return userParameters.hashCode();
    }
}
