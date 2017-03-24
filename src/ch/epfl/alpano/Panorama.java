package ch.epfl.alpano;

import static java.util.Objects.requireNonNull;
import static java.util.Arrays.fill;



/**
 * Classe representant les panorama accompagnee d'un batisseur
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

final class Panorama {
    
    private final PanoramaParameters PARAMETERS;
    private final float[] DISTANCE;
    private final float[] LONGITUDE;
    private final float[] LATITUDE;
    private final float[] ELEVATION;
    private final float[] SLOPE;

    
    private Panorama (PanoramaParameters parameters, float[] distance, float[] longitude, float[] latitude,float[] elevation, float[] slope){
        PARAMETERS = parameters;    
        DISTANCE = distance;
        LONGITUDE = longitude;
        LATITUDE = latitude;
        ELEVATION = elevation;
        SLOPE = slope;
    }
    
    public PanoramaParameters parameters(){
        return PARAMETERS;
    }
    
    private void checkInBound(int x, int y){
        if(PARAMETERS.isValidSampleIndex(x, y)){
            throw new IndexOutOfBoundsException();
        }
    }
    
    public float distanceAt(int x, int y){
        checkInBound(x,y);
        return DISTANCE[PARAMETERS.linearSampleIndex(x, y)];
    }
    
    public float distanceAt(int x, int y, float d){
        if(!(PARAMETERS.isValidSampleIndex(x, y))){
            return d;
        }else {
            return DISTANCE[PARAMETERS.linearSampleIndex(x, y)];
        }
    }
    
    public float longitudeAt(int x, int y){
        checkInBound(x,y);
        return LONGITUDE[PARAMETERS.linearSampleIndex(x, y)];
    }
    
    public float latitudeAt(int x, int y){
        checkInBound(x,y);
        return LATITUDE[PARAMETERS.linearSampleIndex(x, y)];
    }
    
    public float elevationAt(int x, int y){
        checkInBound(x,y);
        return ELEVATION[PARAMETERS.linearSampleIndex(x, y)];
    }
    
    public float slopeAt(int x, int y){
        checkInBound(x,y);
        return SLOPE[PARAMETERS.linearSampleIndex(x, y)];
    }
    
    
    
    public final static class Builder {
        private PanoramaParameters param;
        private float[] distanceBuild, longitudeBuild, latitudeBuild, elevationBuild, slopeBuild;
        private boolean isBuilt = false;
        private final int taille = param.width()*param.height();
        
        public Builder(PanoramaParameters parameters){
            param = requireNonNull(parameters);
            distanceBuild = new float[taille];
            fill(distanceBuild, Float.POSITIVE_INFINITY);
            longitudeBuild = new float[taille];
            latitudeBuild = new float[taille];
            elevationBuild = new float[taille];
            slopeBuild = new float[taille];
                   
        }
        
        public Builder setDistanceAt(int x, int y, float distance){
            if(isBuilt){
                throw new IllegalStateException();
            }
            distanceBuild[param.linearSampleIndex(x, y)] = distance;
            return this;
        }
        
        public Builder setLongitudeAt(int x, int y, float longitude){
            if(isBuilt){
                throw new IllegalStateException();
            }
            longitudeBuild[param.linearSampleIndex(x, y)] = longitude;
            return this;
        }
        
        public Builder setLatitudeAt(int x, int y, float latitude){
            if(isBuilt){
                throw new IllegalStateException();
            }
            latitudeBuild[param.linearSampleIndex(x, y)] = latitude;
            return this;
        }
        
        public Builder setElevationAt(int x, int y, float elevation){
            if(isBuilt){
                throw new IllegalStateException();
            }
            elevationBuild[param.linearSampleIndex(x, y)] = elevation;
            return this;
        }
        
        public Builder setSlopeAt(int x, int y, float slope){
            if(isBuilt){
                throw new IllegalStateException();
            }
            slopeBuild[param.linearSampleIndex(x, y)] = slope;
            return this;
        }
        
        public Panorama build(){
            if(isBuilt){
                throw new IllegalStateException();
            }
            isBuilt = true;
            
            return new Panorama(param, distanceBuild, longitudeBuild, latitudeBuild, elevationBuild, slopeBuild);
        }
        
       
    }

}


