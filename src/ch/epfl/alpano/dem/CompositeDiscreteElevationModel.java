package ch.epfl.alpano.dem;

import ch.epfl.alpano.Interval2D;

/**
 * 
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
final class CompositeDiscreteElevationModel implements DiscreteElevationModel {
    public CompositeDiscreteElevationModel(DiscreteElevationModel dem1, DiscreteElevationModel dem2) {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Interval2D extent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double elevationSample(int x, int y) {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
