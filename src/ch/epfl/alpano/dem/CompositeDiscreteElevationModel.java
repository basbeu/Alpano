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
    private final DiscreteElevationModel DM1, DM2; 
    
    /**
     * Constructeur d'un CompositeDiscreteElevationModel  
     * 
     * @param dem1 DiscreteElevationModel representant le premier modele de terrain
     * @param dem2 DiscreteElevationModel representant le deuxieme modele de terrain
     */
    public CompositeDiscreteElevationModel(DiscreteElevationModel dem1, DiscreteElevationModel dem2) {
        DM1 = requireNonNull(dem1);
        DM2 = requireNonNull(dem2);
    }
    
    @Override
    public void close() throws Exception {  
        DM1.close();
        DM2.close();
    }

    @Override
    public Interval2D extent() {
        return DM1.extent().union(DM2.extent());
    }

    @Override
    public double elevationSample(int x, int y) {
        checkArgument(!(DM1.extent().contains(x,y) || DM2.extent().contains(x, y)));
        if(DM1.extent().contains(x, y)){
            return DM1.elevationSample(x, y);
        }else
            return DM2.elevationSample(x, y);
    }
    
}
