package ch.epfl.alpano;

import static ch.epfl.alpano.Distance.EARTH_RADIUS;
import static ch.epfl.alpano.Math2.firstIntervalContainingRoot;
import static ch.epfl.alpano.Math2.improveRoot;
import static java.lang.Math.tan;
import static java.util.Objects.requireNonNull;

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
    private static final double K = 0.13;
    private static final int INTERVAL_SEARCH = 64;
    private static final int INTERVAL_DICHO = 4;

    private final ContinuousElevationModel dem;

    /**
     * Constructeur de PanoramaComputer
     * @param dem ContinuousElevationModel representant le MNT continus sur lequel on se base
     * @throws NullPointerException le dem est null
     */
    public PanoramaComputer(ContinuousElevationModel dem){
        this.dem = requireNonNull(dem, "ContinuousElevationModel dem : null");
    }

    /**
     * Calcule un panorama en fonction des parametres
     * @param parameters PanoramaParameters representant les parameteres du panorama a calculer
     * @return Panorama representant le panorama
     */
    public Panorama computePanorama(PanoramaParameters parameters){
        Panorama.Builder builder = new Panorama.Builder(parameters); 

        for(int x=0;x<parameters.width();++x){
            ElevationProfile profile= new ElevationProfile(dem, parameters.observerPosition(), parameters.azimuthForX(x),parameters.maxDistance());
            boolean infinityFound=false;
            double distanceX =0;

            int y=parameters.height()-1;
            while(!infinityFound && y>=0){
                DoubleUnaryOperator delta = rayToGroundDistance(profile, parameters.observerElevation(), tan(parameters.altitudeForY(y)));
                double x1= firstIntervalContainingRoot(delta, distanceX, parameters.maxDistance(), INTERVAL_SEARCH);
                if(x1 != Double.POSITIVE_INFINITY){
                    distanceX = improveRoot(delta, x1, x1+INTERVAL_SEARCH,INTERVAL_DICHO);

                    double distance = distanceX/Math.cos(parameters.altitudeForY(y));
                    builder.setDistanceAt(x, y, (float)distance)
                    .setElevationAt(x, y, (float)profile.elevationAt(distanceX))
                    .setLatitudeAt(x, y, (float)profile.positionAt(distanceX).latitude())
                    .setLongitudeAt(x, y, (float)profile.positionAt(distanceX).longitude())
                    .setSlopeAt(x, y, (float)profile.slopeAt(distanceX));                
                }else{
                    infinityFound=true;
                }
                --y;
            }
        }

        return builder.build();
    }

    /**
     * Construit la fonction representant un rayon
     * @param profile ElevationProfile representant le profile sur lequel on envoit le rayon
     * @param ray0 double altitude de depart du rayon 
     * @param raySlope double representant pente du rayon
     * @return un DoubleUnaryOperator representant une fonction donnant la distance entre l
     */
    public static DoubleUnaryOperator rayToGroundDistance(ElevationProfile profile, double ray0, double raySlope){
        return (x)->{return ray0 + x * raySlope - profile.elevationAt(x) + ((1 - K) / (2 * EARTH_RADIUS)) * x * x;};
    }

}
