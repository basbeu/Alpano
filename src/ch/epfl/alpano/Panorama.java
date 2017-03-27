package ch.epfl.alpano;

import static java.util.Objects.requireNonNull;
import static java.util.Arrays.fill;

/**
 * Classe representant les panorama accompagnee d'un batisseur
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

public final class Panorama {
    
    private final PanoramaParameters PARAMETERS;
    private final float[] DISTANCE;
    private final float[] LONGITUDE;
    private final float[] LATITUDE;
    private final float[] ELEVATION;
    private final float[] SLOPE;

    
    /**
     * Constructeur d'un panorama
     * 
     * @param parameters PanoramaParameters parametres du panorama
     * @param distance float[] tableau contenant  les valeurs des echantillons de la distance
     * @param longitude float[] tableau contenant  les valeurs des echantillons de la longitude
     * @param latitude float[] tableau contenant  les valeurs des echantillons de la latitude
     * @param elevation float[] tableau contenant  les valeurs des echantillons de l'elevation
     * @param slope float[] tableau contenant  les valeurs des echantillons de la pente
     */
    private Panorama (PanoramaParameters parameters, float[] distance, float[] longitude, float[] latitude,float[] elevation, float[] slope){
        PARAMETERS = parameters;    
        DISTANCE = distance;
        LONGITUDE = longitude;
        LATITUDE = latitude;
        ELEVATION = elevation;
        SLOPE = slope;
    }
    
    /**
     * Accesseur public des parametres
     * 
     * @return PanoramaParameters parametre du panorama
     */
    public PanoramaParameters parameters(){
        return PARAMETERS;
    }
    
    /**
     * Methode privee pour tester si les coordonnees du point passe sont hors des bornes du panorama
     * 
     * @param x int coordonnee x du point
     * @param y int coordonnee y du point
     * @throws IndexOutOfBoundsException si les coordonnees sont hors des bornes
     */
    private void checkInBound(int x, int y){
        if(!PARAMETERS.isValidSampleIndex(x, y)){
            throw new IndexOutOfBoundsException();
        }
    }
    
    /**
     * Accesseur public du tableau des echantillons de distance
     * 
     * @param x int coorodnnee d'index x
     * @param y int coordonnee d'index y
     * @return float distance à l'index x,y 
     */
    public float distanceAt(int x, int y){
        checkInBound(x,y);
        return DISTANCE[PARAMETERS.linearSampleIndex(x, y)];
    }
    
    /**
     * @param x
     * @param y
     * @param d
     * @return
     */
    public float distanceAt(int x, int y, float d){
        if(!(PARAMETERS.isValidSampleIndex(x, y))){
            return d;
        }else {
            return DISTANCE[PARAMETERS.linearSampleIndex(x, y)];
        }
    }
    
    /**
     * @param x
     * @param y
     * @return
     */
    public float longitudeAt(int x, int y){
        checkInBound(x,y);
        return LONGITUDE[PARAMETERS.linearSampleIndex(x, y)];
    }
    
    /**
     * @param x
     * @param y
     * @return
     */
    public float latitudeAt(int x, int y){
        checkInBound(x,y);
        return LATITUDE[PARAMETERS.linearSampleIndex(x, y)];
    }
    
    /**
     * @param x
     * @param y
     * @return
     */
    public float elevationAt(int x, int y){
        checkInBound(x,y);
        return ELEVATION[PARAMETERS.linearSampleIndex(x, y)];
    }
    
    /**
     * @param x
     * @param y
     * @return
     */
    public float slopeAt(int x, int y){
        checkInBound(x,y);
        return SLOPE[PARAMETERS.linearSampleIndex(x, y)];
    }
    
    /**
     * Classe representant un batisseur de panorama
     * 
     */    
    public final static class Builder {
        private PanoramaParameters param;
        private float[] distanceBuild, longitudeBuild, latitudeBuild, elevationBuild, slopeBuild;
        private boolean isBuilt = false;
<<<<<<< HEAD
=======
        //private final int taille = param.width()*param.height();
>>>>>>> refs/remotes/origin/master
        
        /**
         * Constructeur d'un builder
         * 
         * @param parameters PanoramaParameters parametres du panorama
         */
        public Builder(PanoramaParameters parameters){
<<<<<<< HEAD
            param = requireNonNull(parameters);
            distanceBuild = new float[param.width()*param.height()];
=======
            param = parameters;
            int taille = param.width()*param.height();
            distanceBuild = new float[taille];
>>>>>>> refs/remotes/origin/master
            fill(distanceBuild, Float.POSITIVE_INFINITY);
            longitudeBuild = new float[param.width()*param.height()];
            latitudeBuild = new float[param.width()*param.height()];
            elevationBuild = new float[param.width()*param.height()];
            slopeBuild = new float[param.width()*param.height()];
                   
        }
        
        /**
         * @param isBuilt
         */
        private void checkIsBuilt(boolean isBuilt){
            if(isBuilt){
                throw new IllegalStateException();
            }
        }
        
        /**
         * @param x
         * @param y
         * @param distance
         * @return
         */
        public Builder setDistanceAt(int x, int y, float distance){
            checkIsBuilt(isBuilt);
            distanceBuild[param.linearSampleIndex(x, y)] = distance;
            return this;
        }
        
        /**
         * @param x
         * @param y
         * @param longitude
         * @return
         */
        public Builder setLongitudeAt(int x, int y, float longitude){
            checkIsBuilt(isBuilt);
            longitudeBuild[param.linearSampleIndex(x, y)] = longitude;
            return this;
        }
        
        /**
         * @param x
         * @param y
         * @param latitude
         * @return
         */
        public Builder setLatitudeAt(int x, int y, float latitude){
            checkIsBuilt(isBuilt);
            latitudeBuild[param.linearSampleIndex(x, y)] = latitude;
            return this;
        }
        
        /**
         * @param x
         * @param y
         * @param elevation
         * @return
         */
        public Builder setElevationAt(int x, int y, float elevation){
            checkIsBuilt(isBuilt);
            elevationBuild[param.linearSampleIndex(x, y)] = elevation;
            return this;
        }
        
        /**
         * @param x
         * @param y
         * @param slope
         * @return
         */
        public Builder setSlopeAt(int x, int y, float slope){
            checkIsBuilt(isBuilt);
            slopeBuild[param.linearSampleIndex(x, y)] = slope;
            return this;
        }
        
        /**
         * @return
         */
        public Panorama build(){
            checkIsBuilt(isBuilt);
            isBuilt = true;
            
            return new Panorama(param, distanceBuild, longitudeBuild, latitudeBuild, elevationBuild, slopeBuild);
        }     
    }
}