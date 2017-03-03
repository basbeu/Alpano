package ch.epfl.alpano.dem;

import ch.epfl.alpano.GeoPoint;
import static java.util.Objects.requireNonNull;
import static ch.epfl.alpano.dem.DiscreteElevationModel.sampleIndex;
import static ch.epfl.alpano.Math2.bilerp;
import static java.lang.Math.floor;


/**
 * Represente un MNT continu, obtenu par interpolation d'un MNT discret
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public final class ContinuousElevationModel {
    final private DiscreteElevationModel DEM;
    
    /**
     * Constructeur ContinuousElevationModel qui construit un MNT continu basé sur un MNT discret
     * 
     * @param dem DiscreteElevationModel un MNT discret
     * @throws NullPointerException si le MNT discret passe en argument est nul
     */
    public ContinuousElevationModel(DiscreteElevationModel dem) {
        DEM = requireNonNull(dem);
    }
    
    /**
     * Pour calculer l'altitude au point donné, en mètre, obtenue par interpolation bilineaire de l'extension du MNT discret
     * 
     * @param p GeoPoint point donne en radian
     * @return double la pente du terrain
     */
    public double elevationAt(GeoPoint p){
        double x = sampleIndex(p.longitude());
        double y = sampleIndex(p.latitude());
        
        int x0 = (int)floor(x);
        int y0 = (int)floor(y);
        
        return bilerp(elevationDEMAt(x0, y0), elevationDEMAt(x0+1,y0) ,elevationDEMAt(x0, y0+1) , elevationDEMAt(x0+1,y0+1) ,x,y);
    }
    
    /**
     * @param p
     * @return
     */
    public double slopeAt(GeoPoint p){
        return 0.0;
    }
    
    /**
     * @param x
     * @param y
     * @return
     */
    private double elevationDEMAt(int x, int y){
        //faire le cas en dehors du DEM
        try{
            return DEM.elevationSample(x, y);
        }catch(IllegalArgumentException e){
            return 0;
        }
    }
    
    /**
     * @param x
     * @param y
     * @return
     */
    private double slopeDEMAt(int x, int y){
        return 0.0;
    }
}
