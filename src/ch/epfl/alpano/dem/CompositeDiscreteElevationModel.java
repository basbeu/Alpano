package ch.epfl.alpano.dem;

import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import ch.epfl.alpano.Interval2D;

/**
 * Classe immuable representant l'union de deux modèles du terrain discrets
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
final class CompositeDiscreteElevationModel implements DiscreteElevationModel {
    private final DiscreteElevationModel dem1, dem2; 

    /**
     * Constructeur d'un CompositeDiscreteElevationModel  
     * @param dem1 DiscreteElevationModel representant le premier modele de terrain
     * @param dem2 DiscreteElevationModel representant le deuxieme modele de terrain
     * @throws NullPointerException si dem1 ou dem2 est null
     */
    public CompositeDiscreteElevationModel(DiscreteElevationModel dem1, DiscreteElevationModel dem2) {
        this.dem1 = requireNonNull(dem1, "DiscreteElevation dem1 : null");
        this.dem2 = requireNonNull(dem2, "DiscreteElevation dem2 : null");
    }

    @Override
    public void close() throws Exception {  
        dem1.close();
        dem2.close();
    }

    @Override
    public Interval2D extent() {
        return dem1.extent().union(dem2.extent());
    }

    @Override
    public double elevationSample(int x, int y) {
        checkArgument(dem1.extent().contains(x,y) || dem2.extent().contains(x, y), "Index en dehors du DEM");
        if(dem1.extent().contains(x, y)){
            return dem1.elevationSample(x, y);
        }else{
            return dem2.elevationSample(x, y);
        }
    }
}
