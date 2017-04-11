package ch.epfl.alpano.gui;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe public et immuable represente les parametres d'un panorama d'un point de vue de l'utilisateur final de l'application
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public final class PanoramaUserParameters {

    private Map<UserParameter, Integer> userParameters;

    public PanoramaUserParameters(Map<UserParameter, Integer> userParameters){
        
        Map<UserParameter, Integer> up = new EnumMap<>(UserParameter.class);
        up.put(UserParameter.OBSERVER_LONGITUDE, UserParameter.OBSERVER_LONGITUDE.sanitize(userParameters.get(UserParameter.OBSERVER_LONGITUDE)));
        up.put(UserParameter.OBSERVER_LATITUDE, UserParameter.OBSERVER_LATITUDE.sanitize(userParameters.get(UserParameter.OBSERVER_LATITUDE)));
        up.put(UserParameter.OBSERVER_ELEVATION, UserParameter.OBSERVER_ELEVATION.sanitize(userParameters.get(UserParameter.OBSERVER_ELEVATION)));
        up.put(UserParameter.CENTER_AZIMUTH, UserParameter.CENTER_AZIMUTH.sanitize(userParameters.get(UserParameter.CENTER_AZIMUTH)));
        up.put(UserParameter.HORIZONTAL_FIELD_OF_VIEW, UserParameter.HORIZONTAL_FIELD_OF_VIEW.sanitize(userParameters.get(UserParameter.HORIZONTAL_FIELD_OF_VIEW)));
        up.put(UserParameter.MAX_DISTANCE, UserParameter.MAX_DISTANCE.sanitize(userParameters.get(UserParameter.MAX_DISTANCE)));
        up.put(UserParameter.WIDTH, UserParameter.WIDTH.sanitize(userParameters.get(UserParameter.WIDTH)));
        up.put(UserParameter.HEIGHT, UserParameter.HEIGHT.sanitize(userParameters.get(UserParameter.HEIGHT)));
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
    
    public int panoramaParameters(){
        return 0;
    }
    
    public int panoramaDisplayParameters(){
        return 0;
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
