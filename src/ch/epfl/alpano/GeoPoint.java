package ch.epfl.alpano;

import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.lang.Math.PI;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static ch.epfl.alpano.Azimuth.canonicalize;
import static ch.epfl.alpano.Math2.haversin;
import static ch.epfl.alpano.Distance.EARTH_RADIUS;
import static ch.epfl.alpano.Azimuth.fromMath;

import java.text.DecimalFormat;

/**
 *represente un point a la surface de la Terre dont la position est donnee dans un systeme de coodonnee spherique
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

public final class GeoPoint {
    
    final double LONGITUDE;
    final double LATITUDE;
    /**
     * Constructeur d'un point de coordonnee 
     * 
     * @param longitude Double representant la longitude en radians
     * @param latitude Double representant la latitude en radians
     * @throws IllegalArgumentExcpetion si la longitude n'est pas comprise entre [-PI; PI] et si la latitude n'est
     *          pas comprise entre [-PI/2, PI/2]
     */
    public GeoPoint(double longitude, double latitude){
        checkArgument(-PI <= longitude && longitude <= PI, "longitude invalide");
        checkArgument(-PI/2 <= latitude && latitude <= PI/2, "latitude invalide");
        LONGITUDE = longitude;
        LATITUDE = latitude;
    }
    
    /**
     * methode retournant la longitude
     * @return Double la longitude en radians
     */
    public double longitude(){
        return LONGITUDE;
        
    }
    
    /**
     * methode retournant la latitude
     * @return Double la latitude en radians
     */
    public double latitude(){
        return LATITUDE;
        
    }
    
    /**
     * methode qui retourne la distance en metre separant le recepteur (this) de l'argument (that) 
     * @param that GeoPoint point sur la surface de la Terre avec lequel nous calculons la distance
     * @return Double distance en metre
     */
    public double distanceTo(GeoPoint that){
        double alpha = 2*asin(sqrt(haversin(this.latitude() - that.latitude()) 
                + cos(this.latitude())*cos(that.latitude())*haversin(this.longitude() - that.longitude())));
        
        return alpha*EARTH_RADIUS;
    }
    
    /**
     * methode retournant l'azimut de l'argument (that) par rapport au recepteur (this)
     * @param that GeoPoint permettant de calculer l'azimut 
     * @return Double un azimut
     */
    public double azimuthTo(GeoPoint that){
        double beta = (sin(this.longitude() - that.longitude())*cos(that.latitude()))
                /(cos(this.latitude()*sin(that.latitude()) - sin(this.latitude())*cos(that.latitude())*cos(this.longitude()-that.longitude())));
        
        return fromMath(canonicalize(beta));
    }
    
    /**
     * methode retournant la position en longitude et latitude (en degre) 
     * @return String position en degre entre parenthese separe par une virgule
     */   
    public String toString(){
        DecimalFormat df = new DecimalFormat("0.0000");
        double longitudeDegre = (LONGITUDE*180)/PI;
        double latitudeDegre = (LATITUDE*180)/PI;
        
        return "(" + df.format(longitudeDegre) + "°" + "," + df.format(latitudeDegre) + "°" + ")";
    }
  
}
