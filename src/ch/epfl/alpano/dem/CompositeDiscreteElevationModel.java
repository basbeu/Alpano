package ch.epfl.alpano.dem;

import ch.epfl.alpano.Interval2D;
import static java.util.Objects.requireNonNull;
import static ch.epfl.alpano.Preconditions.checkArgument;

/**
 * Represente l'union de deux modèles du terrain discrets
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
final class CompositeDiscreteElevationModel implements DiscreteElevationModel {
    private final DiscreteElevationModel DEM1, DEM2; 
    
    /**
     * Constructeur d'un CompositeDiscreteElevationModel  
     * 
     * @param dem1 DiscreteElevationModel representant le premier modele de terrain
     * @param dem2 DiscreteElevationModel representant le deuxieme modele de terrain
     */
    public CompositeDiscreteElevationModel(DiscreteElevationModel dem1, DiscreteElevationModel dem2) {
        DEM1 = requireNonNull(dem1);
        DEM2 = requireNonNull(dem2);
    }
    
    @Override
    public void close() throws Exception {  
        DEM1.close();
        DEM2.close();
    }

    @Override
    public Interval2D extent() {
        return DEM1.extent().union(DEM2.extent());
    }

    @Override
    public double elevationSample(int x, int y) {
        checkArgument(!(DEM1.extent().contains(x,y) || DEM2.extent().contains(x, y)));
        if(DEM1.extent().contains(x, y)){
            return DEM1.elevationSample(x, y);
        }else
            return DEM2.elevationSample(x, y);
    }
    
}
