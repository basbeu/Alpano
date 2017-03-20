package ch.epfl.alpano;

import static ch.epfl.alpano.Azimuth.isCanonical;
import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static ch.epfl.alpano.Math2.PI2;
import static ch.epfl.alpano.Azimuth.canonicalize;


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
        return  canonicalize((delta*x)- (WIDTH/2) + CENTERAZIMUTH); 
        //return canonicalize(CENTERAZIMUTH + (x-(WIDTH/2))*delta);
    }
    
    public double xForAzimuth(double a){
        checkArgument(a<CENTERAZIMUTH+HORIZONTALFIELDOFVIEW && a>CENTERAZIMUTH-HORIZONTALFIELDOFVIEW);
        
    }
    
    public double altitudeForY(double y){
        checkArgument(y<0 && y>(HEIGHT-1));
        double delta = HORIZONTALFIELDOFVIEW/(WIDTH-1);
        return canonicalize((delta*y)-(HEIGHT/2));
        //return canonicalize(delta*(y-(HEIGHT/2)));
    }
    
    public double yForAltitude(double a){
        checkArgument(a<verticalFieldOfView() && a>(PI2-verticalFieldOfView()));
        

    }
    
    boolean isValidSampleIndex(int x, int y){
        if(x > 0 && x < WIDTH && y > 0 && y < HEIGHT ){
            return true;
        }else
            return false;
    }
    
    int linearSampleIndex(int x, int y){
        if(y==0){
            return x;
        }else if(x==WIDTH-1 && y==HEIGHT-1){
            return WIDTH*HEIGHT-1;
        }else{
            return (WIDTH-1)*y + x;
        }
        
    }

}
