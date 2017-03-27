package ch.epfl.alpano;

import static java.util.Objects.requireNonNull;
import static java.lang.Math.tan;
import static ch.epfl.alpano.Distance.EARTH_RADIUS;
import static ch.epfl.alpano.Math2.firstIntervalContainingRoot;
import static ch.epfl.alpano.Math2.improveRoot;

import java.util.function.DoubleUnaryOperator;

import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.ElevationProfile;

/**
 * Classe immuable representant un calculateur de panorama
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
final public class PanoramaComputer {
    private final ContinuousElevationModel DEM;
    private static final double K=0.13;
    
    public PanoramaComputer(ContinuousElevationModel dem){
        DEM = requireNonNull(dem);
    }
    
    public Panorama computePanorama(PanoramaParameters parameters){
        Panorama.Builder builder = new Panorama.Builder(parameters); 
           
        for(int x=0;x<parameters.width();++x){
            ElevationProfile profile= new ElevationProfile(DEM, parameters.observerPosition(), parameters.azimuthForX(x),parameters.maxDistance());
            boolean infinityFound=false;
            for(int y=parameters.height()-1;y>=0;--y){

                if(!infinityFound){
                    DoubleUnaryOperator delta = rayToGroundDistance(profile, profile.elevationAt(0), tan(parameters.altitudeForY(y)));
                    double x1= firstIntervalContainingRoot(delta, 0,parameters.maxDistance(), 64);
                    System.out.println(x1);
                    if(x1 != Double.POSITIVE_INFINITY){
                        double distance = improveRoot(delta, x1, x1+63,4);
                        builder.setDistanceAt(x, y, (float)distance);
                    }else{
                        infinityFound=true;
                    }
                    
                }
                
                builder.setElevationAt(x, y, (float)parameters.altitudeForY(y));
                builder.setLatitudeAt(x, y, (float)profile.positionAt(y).latitude());
                builder.setLongitudeAt(x, y, (float)profile.positionAt(y).longitude());
                builder.setSlopeAt(x, y, (float)profile.slopeAt(y));
            }
             
        }
        
        
        return builder.build();
    }
    
    public static DoubleUnaryOperator rayToGroundDistance(ElevationProfile profile, double ray0, double raySlope){
        return (x)->{return ray0+x*raySlope-profile.elevationAt(x)+(1-K)/(2*EARTH_RADIUS)*x*x;};
    }
    
}
