package ch.epfl.alpano;

import static ch.epfl.alpano.Azimuth.isCanonical;
import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static ch.epfl.alpano.Math2.PI2;
import static ch.epfl.alpano.Azimuth.canonicalize;
import static java.lang.Math.tan;


final public class PanoramaParameters {
    
    private final GeoPoint OBSERVERPOSITION;
   
    private final int OBSERVER_ELEVATION;
    private final double CENTER_AZIMUTH;
    private final double HORIZONTAL_FIELD_OF_VIEW;
    private final int MAX_DISTANCE;
    private final int WIDTH;
    private final int HEIGHT;
    private final double DELTA;
    
    public PanoramaParameters(GeoPoint observerPosition, int observerElevation, double centerAzimuth, double horizontalFieldOfView, int maxDistance, int width, int height){
        checkArgument(isCanonical(centerAzimuth));
        checkArgument(0 < horizontalFieldOfView && horizontalFieldOfView <= PI2);
        checkArgument(maxDistance > 0 && width > 0 && height > 0);

        OBSERVERPOSITION = requireNonNull(observerPosition);
        OBSERVER_ELEVATION = observerElevation;
        CENTER_AZIMUTH = centerAzimuth;
        HORIZONTAL_FIELD_OF_VIEW = horizontalFieldOfView;
        MAX_DISTANCE = maxDistance;
        WIDTH = width;
        HEIGHT = height;
        
        DELTA = HORIZONTAL_FIELD_OF_VIEW/(WIDTH-1);
    }
    
    public GeoPoint observerPosition (){
        return OBSERVERPOSITION;
    }
    
    public int observerElevation(){
        return OBSERVER_ELEVATION;
    }
    
    public double centerAzimuth(){
        return CENTER_AZIMUTH;
    }
    
    public double horizontalFieldOfView(){
        return HORIZONTAL_FIELD_OF_VIEW;
    }
    
    public int maxDistance(){
        return MAX_DISTANCE;
    }
    
    public int width(){
        return WIDTH;
    }
    
    public int height(){
        return HEIGHT;
    }
    
    public double verticalFieldOfView(){
        return HORIZONTAL_FIELD_OF_VIEW *(HEIGHT-1)/(WIDTH-1);
    }
    
    public double azimuthForX(double x){
        checkArgument(x >= 0 && x <= (WIDTH -1));
        return  canonicalize(CENTER_AZIMUTH+(DELTA*x)-HORIZONTAL_FIELD_OF_VIEW/2); 
    }
    
    public double xForAzimuth(double a){
        checkArgument(a <= CENTER_AZIMUTH + HORIZONTAL_FIELD_OF_VIEW/2 && a >= CENTER_AZIMUTH - HORIZONTAL_FIELD_OF_VIEW/2);
        double alpha = a - CENTER_AZIMUTH + HORIZONTAL_FIELD_OF_VIEW/2;
        return alpha/DELTA;
    }
    
    public double altitudeForY(double y){
        checkArgument(y >= 0 && y <= (HEIGHT-1));
        return canonicalize((verticalFieldOfView()/2) - (DELTA*y));
    }
    
    public double yForAltitude(double a){
        checkArgument(a <= verticalFieldOfView()/2 && a >= -verticalFieldOfView()/2);
        return a/DELTA;
    }
    
    boolean isValidSampleIndex(int x, int y){
        if(x > 0 && x < WIDTH && y > 0 && y < HEIGHT ){
            return true;
        }else
            return false;
    }
    
    int linearSampleIndex(int x, int y){
        return x + WIDTH*y;
    }

}
