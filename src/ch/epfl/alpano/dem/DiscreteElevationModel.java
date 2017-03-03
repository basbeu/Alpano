package ch.epfl.alpano.dem;

import static java.lang.Math.PI;

import ch.epfl.alpano.Interval2D;

/**
 * Interface representant un MNT discret
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public interface DiscreteElevationModel extends AutoCloseable{
    int SAMPLES_PER_DEGREE = 3600;
    double SAMPLES_PER_RADIAN = SAMPLES_PER_DEGREE*PI/180;
    
    /**
     * @param angle double representant un angle en radian
     * @return l'index correspondant à l'angle passe en parametre
     */
    static double sampleIndex(Double angle){
        return angle * SAMPLES_PER_RADIAN;
    }
   
    /**
     * @return un Interval2D representant l'etendu du MNT
     */
    Interval2D extent();
    
    /**
     * @param x int representant la composante x de l'index
     * @param y int representant la composante y de l'index 
     * @return un double representant l'echantillon d'altitude (en metre) correspondant a l'index
     * @throws IllegalArgumentException si l'index ne fait pas partie de l'etendue du MNT
     */
    double elevationSample(int x, int y);
    
    
    /**
     * @param that DiscreteElevationModel representant le MNT discret avec lequel il faut proceder a l'union
     * @return DiscreteElevationModel representant un MNT discret representant l'union de that et de this
     * @throws IllegalArgumentException si les etendues ne sont pas unionables
     */
    default DiscreteElevationModel union(DiscreteElevationModel that){
        return new CompositeDiscreteElevationModel(this, that);
    }
}
