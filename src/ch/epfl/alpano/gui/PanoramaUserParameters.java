package ch.epfl.alpano.gui;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Classe public et immuable represente les parametres d'un panorama d'un point de vue de l'utilisateur final de l'application
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public final class PanoramaUserParameters {

    private Map<UserParameter, Integer> userParameters;

    public PanoramaUserParameters(Map<UserParameter, Integer> userParameters){
        this.userParameters = Collections.unmodifiableMap(new EnumMap<>(userParameters));
    }

    public PanoramaUserParameters(int observerLongitude, int observerLatitude, int observeElevation, int centerAzimuth, int horizontalFielOfView, int maxDistance, int width, int height, int superSamplingExponent){
        
        //this(new EnumMap<>(UserParameter.class));

        Map<UserParameter, Integer> up = new EnumMap<>(UserParameter.class);
        up.put(UserParameter.OBSERVER_LONGITUDE, UserParameter.OBSERVER_LONGITUDE.sanitize(observerLongitude));
        up.put(UserParameter.OBSERVER_LATITUDE, UserParameter.OBSERVER_LATITUDE.sanitize(observerLatitude));
        up.put(UserParameter.OBSERVER_ELEVATION, UserParameter.OBSERVER_ELEVATION.sanitize(observeElevation));
        up.put(UserParameter.CENTER_AZIMUTH, UserParameter.CENTER_AZIMUTH.sanitize(centerAzimuth));
        up.put(UserParameter.HORIZONTAL_FIELD_OF_VIEW, UserParameter.HORIZONTAL_FIELD_OF_VIEW.sanitize(horizontalFielOfView));
        up.put(UserParameter.MAX_DISTANCE, UserParameter.MAX_DISTANCE.sanitize(maxDistance));
        up.put(UserParameter.WIDTH, UserParameter.WIDTH.sanitize(width));
        up.put(UserParameter.HEIGHT, UserParameter.HEIGHT.sanitize(height));
        up.put(UserParameter.SUPER_SAMPLING_EXPONENT, UserParameter.SUPER_SAMPLING_EXPONENT.sanitize(superSamplingExponent));
        
        new PanoramaUserParameters(up);

    }
    
    public Integer get(UserParameter up){
        return userParameters.get(up);
    }
    
    public Integer observerLongitude(){
        return userParameters.get(UserParameter.OBSERVER_LONGITUDE);
    }
    
    public Integer observerLatitude(){
        return userParameters.get(UserParameter.OBSERVER_LATITUDE);
    }
    
    public Integer observerElevation(){
        return userParameters.get(UserParameter.OBSERVER_ELEVATION);
    }
    
    public Integer centerAzimuth(){
        return userParameters.get(UserParameter.CENTER_AZIMUTH);
    }
    
    public Integer horizontalFieldOfView(){
        return userParameters.get(UserParameter.HORIZONTAL_FIELD_OF_VIEW);
    }
    
    public Integer maxDistance(){
        return userParameters.get(UserParameter.MAX_DISTANCE);
    }
    
    public Integer width(){
        return userParameters.get(UserParameter.WIDTH);
    }
    
    public Integer heigth(){
        return userParameters.get(UserParameter.HEIGHT);
    }
    
    public Integer superSampleExponent(){
        return userParameters.get(UserParameter.SUPER_SAMPLING_EXPONENT);
    }
    
    public int panoramaParameters(){
        return 0;
    }
    
    public int panoramaDisplayParameters(){
        return 0;
    }
    

}
