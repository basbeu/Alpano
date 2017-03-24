package ch.epfl.alpano;

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
    private final ContinuousElevationModel DEM;
    
    public PanoramaComputer(ContinuousElevationModel dem){
        DEM = requireNonNull(dem);
    }
    
    public Panorama computePanorama(PanoramaParameters parameters){
        return null;
    }
    
    public static DoubleUnaryOperator rayToGroundDistance(ElevationProfile profile, double ray0, double raySlope){
        return (x)->{return 0.;};
    }
    
}
