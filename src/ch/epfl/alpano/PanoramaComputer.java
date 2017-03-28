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
    private final int INTERVAL_SEARCH = 64;
    private final int INTERVAL_DICHO = 4;

    public PanoramaComputer(ContinuousElevationModel dem){
        DEM = requireNonNull(dem);
    }

    public Panorama computePanorama(PanoramaParameters parameters){
        Panorama.Builder builder = new Panorama.Builder(parameters); 

        for(int x=0;x<parameters.width();++x){
            ElevationProfile profile= new ElevationProfile(DEM, parameters.observerPosition(), parameters.azimuthForX(x),parameters.maxDistance());
            boolean infinityFound=false;
            double distanceX =0;
            
            int y=parameters.height()-1;
            while(!infinityFound && y>=0){
                DoubleUnaryOperator delta = rayToGroundDistance(profile, parameters.observerElevation(), tan(parameters.altitudeForY(y)));
                double x1= firstIntervalContainingRoot(delta, distanceX,parameters.maxDistance(), INTERVAL_SEARCH);
                if(x1 != Double.POSITIVE_INFINITY){
                    distanceX = improveRoot(delta, x1, x1+INTERVAL_SEARCH,INTERVAL_DICHO);
                    
                    double distance = distanceX/Math.cos(parameters.altitudeForY(y));
                    builder.setDistanceAt(x, y, (float)distance);

                    builder.setElevationAt(x, y, (float)profile.elevationAt(distanceX));
                    builder.setLatitudeAt(x, y, (float)profile.positionAt(distanceX).latitude());
                    builder.setLongitudeAt(x, y, (float)profile.positionAt(distanceX).longitude());
                    builder.setSlopeAt(x, y, (float)profile.slopeAt(distanceX));
                    
                }else{
                    infinityFound=true;
                }
                --y;
            }

        }


        return builder.build();
    }

    public static DoubleUnaryOperator rayToGroundDistance(ElevationProfile profile, double ray0, double raySlope){
        return (x)->{return ray0+x*raySlope-profile.elevationAt(x)+((1-K)/(2*EARTH_RADIUS))*x*x;};
    }

}
