package ch.epfl.alpano.dem;

import static ch.epfl.alpano.Distance.toMeters;
import static ch.epfl.alpano.Math2.bilerp;
import static ch.epfl.alpano.Math2.sq;
import static ch.epfl.alpano.dem.DiscreteElevationModel.SAMPLES_PER_RADIAN;
import static ch.epfl.alpano.dem.DiscreteElevationModel.sampleIndex;
import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.floor;
import static java.lang.Math.sqrt;
import static java.util.Objects.requireNonNull;

import ch.epfl.alpano.GeoPoint;

/**
 * Classe immuable representant un MNT continu, obtenu par interpolation d'un MNT discret
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public final class ContinuousElevationModel {
    private final static double DISTANCE = toMeters(1/SAMPLES_PER_RADIAN); 

    private final DiscreteElevationModel dem;

    /**
     * Constructeur ContinuousElevationModel qui construit un MNT continu basé sur un MNT discret
     * 
     * @param dem DiscreteElevationModel un MNT discret
     * @throws NullPointerException si le MNT discret passe en argument est nul
     */
    public ContinuousElevationModel(DiscreteElevationModel dem) {
        this.dem = requireNonNull(dem, "DiscreteElevation dem : null");
    }

    /**
     * Pour calculer l'altitude au point donné, en mètre, obtenue par interpolation bilineaire de l'extension du MNT discret
     * 
     * @param p GeoPoint point donne en radian
     * @return double l'altitude du point donne
     */
    public double elevationAt(GeoPoint p){
        double x = sampleIndex(p.longitude());
        double y = sampleIndex(p.latitude());

        int x0 = (int)floor(x);
        int y0 = (int)floor(y);

        return bilerp(elevationDEMAt(x0, y0), elevationDEMAt(x0+1,y0), elevationDEMAt(x0, y0+1), elevationDEMAt(x0+1,y0+1), x-x0, y-y0);
    }

    /**
     * Pour calculer la pente du terrain au point donne, en radians
     * 
     * @param p Geopoint donne en radian
     * @return double la pente du terrain
     */
    public double slopeAt(GeoPoint p){
        double x = sampleIndex(p.longitude());
        double y = sampleIndex(p.latitude());

        int x0 = (int)floor(x);
        int y0 = (int)floor(y);

        return bilerp(slopeDEMAt(x0,y0),slopeDEMAt(x0+1,y0),slopeDEMAt(x0,y0+1),slopeDEMAt(x0+1,y0+1),x-x0,y-y0);
    }

    /**
     * Methode privee retournant l'altitude d'un point d'extension du DEM discret passe au constructeur
     * 
     * @param x Int composante x de l'index
     * @param y Int composante y de l'index
     * @return Double altitude en metre 
     */
    private double elevationDEMAt(int x, int y){
        if(dem.extent().contains(x, y)){
            return dem.elevationSample(x, y);
        }else{
            return 0;
        }
    }

    /**
     * Methode privee retournant la pente d'un point de l'extension du DEM discret passe au constructeur
     * 
     * @param x Int composante x de l'index
     * @param y Int composante y de l'index
     * @return Double pente en radian 
     */
    private double slopeDEMAt(int x, int y){
        double za = abs(elevationDEMAt(x,y) - elevationDEMAt(x+1, y));
        double zb = abs(elevationDEMAt(x,y) - elevationDEMAt(x, y+1));

        return acos(DISTANCE / sqrt(sq(za) + sq(zb) + sq(DISTANCE)));
    }
}
