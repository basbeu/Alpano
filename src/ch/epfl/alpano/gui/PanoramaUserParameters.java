package ch.epfl.alpano.gui;

import static java.lang.Math.toRadians;
import static java.lang.Math.pow;
import static ch.epfl.alpano.Preconditions.checkArgument;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
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
    
    private static final int TO_KM = 1_000;
    private static final int MAX_VERTICAL_FIELD_OF_VIEW = 170;
    
    private Map<UserParameter, Integer> userParameters;
    
    public PanoramaUserParameters(Map<UserParameter, Integer> userParameters){
        for(UserParameter param : UserParameter.values())
            checkArgument(userParameters.containsKey(param),"La Map doit contenir tous les paramêtres");
            
        //controle de la hauteur
        int height = UserParameter.HEIGHT.sanitize(userParameters.get(UserParameter.HEIGHT));
        int width =  UserParameter.WIDTH.sanitize(userParameters.get(UserParameter.WIDTH));
        int vh = UserParameter.HORIZONTAL_FIELD_OF_VIEW.sanitize(userParameters.get(UserParameter.HORIZONTAL_FIELD_OF_VIEW));
        
        if(((height-1)/(width-1))*vh > MAX_VERTICAL_FIELD_OF_VIEW)
            height = (MAX_VERTICAL_FIELD_OF_VIEW*(width-1))/vh;
        
        Map<UserParameter, Integer> up = new EnumMap<>(UserParameter.class);
        up.put(UserParameter.OBSERVER_LONGITUDE, UserParameter.OBSERVER_LONGITUDE.sanitize(userParameters.get(UserParameter.OBSERVER_LONGITUDE)));
        up.put(UserParameter.OBSERVER_LATITUDE, UserParameter.OBSERVER_LATITUDE.sanitize(userParameters.get(UserParameter.OBSERVER_LATITUDE)));
        up.put(UserParameter.OBSERVER_ELEVATION, UserParameter.OBSERVER_ELEVATION.sanitize(userParameters.get(UserParameter.OBSERVER_ELEVATION)));
        up.put(UserParameter.CENTER_AZIMUTH, UserParameter.CENTER_AZIMUTH.sanitize(userParameters.get(UserParameter.CENTER_AZIMUTH)));
        up.put(UserParameter.HORIZONTAL_FIELD_OF_VIEW, vh);
        up.put(UserParameter.MAX_DISTANCE, UserParameter.MAX_DISTANCE.sanitize(userParameters.get(UserParameter.MAX_DISTANCE)));
        up.put(UserParameter.WIDTH, width);
        up.put(UserParameter.HEIGHT, height);
        up.put(UserParameter.SUPER_SAMPLING_EXPONENT, UserParameter.SUPER_SAMPLING_EXPONENT.sanitize(userParameters.get(UserParameter.SUPER_SAMPLING_EXPONENT)));
                
        this.userParameters = Collections.unmodifiableMap(new EnumMap<>(up));
    }

    public PanoramaUserParameters(int observerLongitude, int observerLatitude, int observerElevation, int centerAzimuth, int horizontalFieldOfView, int maxDistance, int width, int height, int superSamplingExponent){
        
        this(new HashMap<UserParameter, Integer>(){{
            put(UserParameter.OBSERVER_LONGITUDE, observerLongitude);
            put(UserParameter.OBSERVER_LATITUDE, observerLatitude);
            put(UserParameter.OBSERVER_ELEVATION, observerElevation);
            put(UserParameter.CENTER_AZIMUTH, centerAzimuth);
            put(UserParameter.HORIZONTAL_FIELD_OF_VIEW, horizontalFieldOfView);
            put(UserParameter.MAX_DISTANCE, maxDistance);
            put(UserParameter.WIDTH, width);
            put(UserParameter.HEIGHT, height);
            put(UserParameter.SUPER_SAMPLING_EXPONENT, superSamplingExponent);
        }});

    }
    
    private double tenThousandthDegreesToRadian(int degree){
        return toRadians(degree/10000);
    }
    
    private GeoPoint observerPosition(){
        return new GeoPoint(tenThousandthDegreesToRadian(observerLongitude()), tenThousandthDegreesToRadian(observerLatitude())); 
    }
    
    public int get(UserParameter up){
        return userParameters.get(up);
    }
    
    public int observerLongitude(){
        return userParameters.get(UserParameter.OBSERVER_LONGITUDE);
    }
    
    public int observerLatitude(){
        return userParameters.get(UserParameter.OBSERVER_LATITUDE);
    }
    
    public int observerElevation(){
        return userParameters.get(UserParameter.OBSERVER_ELEVATION);
    }
    
    public int centerAzimuth(){
        return userParameters.get(UserParameter.CENTER_AZIMUTH);
    }
    
    public int horizontalFieldOfView(){
        return userParameters.get(UserParameter.HORIZONTAL_FIELD_OF_VIEW);
    }
    
    public int maxDistance(){
        return userParameters.get(UserParameter.MAX_DISTANCE);
    }
    
    public int width(){
        return userParameters.get(UserParameter.WIDTH);
    }
    
    public int height(){
        return userParameters.get(UserParameter.HEIGHT);
    }
    
    public int superSamplingExponent(){
        return userParameters.get(UserParameter.SUPER_SAMPLING_EXPONENT);
    }
    
    public PanoramaParameters panoramaParameters(){
        int wp = (int)pow(2, superSamplingExponent())*width();
        int hp = (int)pow(2, superSamplingExponent())*height();
        
        return new PanoramaParameters(observerPosition(), observerElevation(), toRadians(centerAzimuth()), toRadians(horizontalFieldOfView()), maxDistance() * TO_KM, wp, hp);
    }
    
    public PanoramaParameters panoramaDisplayParameters(){
        return new PanoramaParameters(observerPosition(), observerElevation(), toRadians(centerAzimuth()), toRadians(horizontalFieldOfView()), maxDistance() * TO_KM, width(), height());
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
