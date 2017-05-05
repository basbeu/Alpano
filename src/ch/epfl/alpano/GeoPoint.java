package ch.epfl.alpano;

import static ch.epfl.alpano.Azimuth.canonicalize;
import static ch.epfl.alpano.Azimuth.fromMath;
import static ch.epfl.alpano.Math2.haversin;
import static ch.epfl.alpano.Preconditions.checkArgument;
import static ch.epfl.alpano.Distance.toMeters;
import static java.lang.Math.PI;
import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;

import java.util.Locale;

/**
 *Classe immuable representant un point a la surface de la Terre dont la position est donnee dans un systeme de coodonnee spherique
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

public final class GeoPoint {

    private final double longitude;
    private final double latitude;

    /**
     * Constructeur d'un point de coordonnee 
     * @param longitude double representant la longitude en radians
     * @param latitude double representant la latitude en radians
     * @throws IllegalArgumentException si la longitude n'est pas comprise entre [-PI; PI] ou si la latitude n'est
     *          pas comprise entre [-PI/2, PI/2]
     */
    public GeoPoint(double longitude, double latitude){
        checkArgument(-PI <= longitude && longitude <= PI, "longitude invalide");
        checkArgument(-PI / 2 <= latitude && latitude <= PI / 2, "latitude invalide");
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Accesseur public de la longitude du point
     * @return double la longitude en radians
     */
    public double longitude(){
        return longitude;

    }

    /**
     * Accesseur public de la latitude du point
     * @return double la latitude en radians
     */
    public double latitude(){
        return latitude;

    }

    /**
     * Calcul la distance en metre separant le recepteur (this) de l'argument (that) 
     * @param that GeoPoint point sur la surface de la Terre avec lequel nous calculons la distance
     * @return double distance en metre
     */
    public double distanceTo(GeoPoint that){
        double alpha = 2 * asin(sqrt(haversin(this.latitude() - that.latitude()) 
                + cos(this.latitude()) * cos(that.latitude()) * haversin(this.longitude() - that.longitude())));

        return toMeters(alpha);
    }

    /**
     * Calcul l'azimut de l'argument (that) par rapport au recepteur (this)
     * @param that GeoPoint permettant de calculer l'azimut 
     * @return double un azimut
     */
    public double azimuthTo(GeoPoint that){
        double beta = atan2(sin(this.longitude() - that.longitude()) * cos(that.latitude()), 
                cos(this.latitude()) * sin(that.latitude()) - sin(this.latitude()) * cos(that.latitude()) * cos(this.longitude() - that.longitude()));

        return fromMath(canonicalize(beta));
    }

    @Override  
    public String toString(){
        Locale l = null;
        return String.format(l, "(%.4f,%.4f)", toDegrees(longitude()), toDegrees(latitude()));
    }

}
