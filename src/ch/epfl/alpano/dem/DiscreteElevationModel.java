package ch.epfl.alpano.dem;

import ch.epfl.alpano.Interval2D;

/**
 * 
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public interface DiscreteElevationModel extends AutoCloseable{
    int SAMPLES_PER_DEGREE = 3600;
    double SAMPLES_PER_RADIAN =0;
    
    static double sampleIndex(Double angle){
        return 0.0;
    }
    
    abstract Interval2D extent();
    
    abstract double elevationSample(int x, int y);
    
    default DiscreteElevationModel union(DiscreteElevationModel that){
        return null;
    }
}
