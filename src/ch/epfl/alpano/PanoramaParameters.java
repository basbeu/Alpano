package ch.epfl.alpano;

import static ch.epfl.alpano.Azimuth.isCanonical;
import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static ch.epfl.alpano.Math2.PI2;


final public class PanoramaParameters {
    
    private final GeoPoint OBSERVERPOSITION;
    private final int OBSERVERELEVATION;
    private final double CENTERAZIMUTH;
    private final double HORIZONTALFIELDOFVIEW;
    private final int MAXDISTANCE;
    private final int WIDTH;
    private final int HEIGHT;
    
    public PanoramaParameters(GeoPoint observerPosition, int observerElevation, double centerAzimuth, double horizontalFieldOfView, int maxDistance, int width, int height){
        checkArgument(isCanonical(centerAzimuth));
        checkArgument(0 < horizontalFieldOfView && horizontalFieldOfView >= PI2);
        checkArgument(maxDistance > 0 && width > 0 && height > 0);

        OBSERVERPOSITION = requireNonNull(observerPosition);
        OBSERVERELEVATION = observerElevation;
        CENTERAZIMUTH = centerAzimuth;
        HORIZONTALFIELDOFVIEW = horizontalFieldOfView;
        MAXDISTANCE = maxDistance;
        WIDTH = width;
        HEIGHT = height;
    }
    
    public GeoPoint observerPosition (){
        return OBSERVERPOSITION;
    }
    
    public int observerElevation(){
        return OBSERVERELEVATION;
    }
    
    public double centerAzimuth(){
        return CENTERAZIMUTH;
    }
    
    public double horizontalFieldOfView(){
        return HORIZONTALFIELDOFVIEW;
    }
    
    public int maxDistance(){
        return MAXDISTANCE;
    }
    
    public int width(){
        return WIDTH;
    }
    
    public int height(){
        return HEIGHT;
    }
    
    public double verticalFieldOfView(){
        return HORIZONTALFIELDOFVIEW*((HEIGHT-1)/(WIDTH-1));
    }
    
    public double azimuthForX(double x){
        checkArgument(x < 0 && x > (WIDTH -1));
        double delta = HORIZONTALFIELDOFVIEW/(WIDTH-1);
        return 
    }
    
    public double xForAzimuth(double a){
        
    }
    
    public double altitudeForY(double y){
        
    }
    
    public double yForAltitude(double a){
        
    }
    
    boolean isValidSampleIndex(int x, int y){
        
    }
    
    int linearSampleIndex(int x, int y){
        
    }

}
