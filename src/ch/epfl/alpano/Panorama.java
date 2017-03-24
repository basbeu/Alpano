package ch.epfl.alpano;


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
    private final float[] SLOAP;

    
    private Panorama (PanoramaParameters parameters, float[] distance, float[] longitude, float[] latitude,float[] elevation, float[] sloap){
        PARAMETERS = parameters;    
        DISTANCE = distance;
        LONGITUDE = longitude;
        LATITUDE = latitude;
        ELEVATION = elevation;
        SLOAP = sloap;
    }
    
    public PanoramaParameters parameters(){
        return PARAMETERS;
    }
    
    public float distanceAt(int x, int y){
        return DISTANCE[];
    }
    
    public float longitudeAt(int x, int y){
        return;
    }
    
    public float latitudeAt(int x, int y){
        return;
    }
    
    public float elevationAt(int x, int y){
        return;
    }
    
    public float slopeAt(int x, int y){
        return;
    }
    
    public float distanceAt(int x, int y, float d){
        return;
    }
    
    public final static Builder {
        public Builder(PanoramaParameters parameters){
            
        }
        
        public Builder setDistanceAt(int x, int y, float distance){
            
        }
        
        public Builder setLongitudeAt(int x, int y, float longitude){
            
        }
        
        public Builder setLatitudeAt(int x, int y, float latitude){
            
        }
        
        public Builder setElevationAt(int x, int y, float elevation){
            
        }
        
        public Builder setSlopeAt(int x, int y, float slope){
            
        }
        
        public Panorama build(){
            
        }
        
       
    }

}

