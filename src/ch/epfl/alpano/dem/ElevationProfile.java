package ch.epfl.alpano.dem;  

import static ch.epfl.alpano.Azimuth.isCanonical;
import static ch.epfl.alpano.Azimuth.toMath;
import static ch.epfl.alpano.Distance.toRadians;
import static ch.epfl.alpano.Math2.PI2;
import static ch.epfl.alpano.Math2.floorMod;
import static ch.epfl.alpano.Math2.lerp;
import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.lang.Math.PI;
import static java.lang.Math.asin;
import static java.lang.Math.ceil;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.pow;
import static java.lang.Math.scalb;
import static java.lang.Math.sin;
import static java.util.Objects.requireNonNull;

import ch.epfl.alpano.GeoPoint;

/**
 * Classe immuable representant un profil altimetrique suivant un arc de grand cercle
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

public final class ElevationProfile {
    private final static int SPACING_EXPONENT = 12;
    private final static int SPACING = (int)pow(2, SPACING_EXPONENT);

    private final ContinuousElevationModel elevation;
    private final double length;
    private final GeoPoint[] tab;

    /**
     * Constructeur d'un profil altimetrique
     * @param elevationModel ContinuousElevationModel MNT donne
     * @param origin GeoPoint qui est le debut du trace
     * @param azimuth double direction du grand cercle
     * @param length double longueur du trace
     * @throws IllegalArgumentException si l'azimuth n'est pas canonique et la longueur n'est pas strictement positive
     * @throws NullPointerException  si l'azimut ou l'origine est null
     */
    public ElevationProfile (ContinuousElevationModel elevationModel, GeoPoint origin, double azimuth, double length){
        checkArgument(isCanonical(azimuth) && length > 0,"azimuth doit etre canonique et length >0");

        elevation = requireNonNull(elevationModel, "ContinuousElevationModel elevationModel : null");
        this.length = length;

        tab = new GeoPoint [(int) (ceil(scalb(length, -SPACING_EXPONENT)) + 1)];
        for(int i=0; i<tab.length; i++){
            double latitude = asin(sin(origin.latitude()) * cos(toRadians(SPACING * i)) + cos(origin.latitude()) * sin(toRadians(SPACING * i)) * cos(toMath(azimuth)));       
            double longitude = (floorMod(origin.longitude() - asin(sin(toMath(azimuth)) * sin(toRadians(SPACING * i)) / cos(latitude)) + PI, PI2) - PI);

            tab[i] = new GeoPoint(longitude, latitude);        
        }
    }

    /**
     * Methode retournant l'altitude du terrain a la position donnee du profil
     * @param x double position 
     * @return double altitude 
     * @throws IllegalArgumentException si la position n'est pas dans les bornes du profil
     */

    public double elevationAt(double x){
        isIn(x, length);

        return elevation.elevationAt(positionAt(x));
    }

    /**
     * Methode retournant les coordonnees du point a la position donnee du profil
     * @param x double position
     * @return GeoPoint coordonnees du point
     * @throws IllegalArgumentException si la position n'est pas dans les bornes du profil 
     */
    public GeoPoint positionAt(double x){
        isIn(x, length);

        double index = scalb(x,-SPACING_EXPONENT);
        int borneInf = (int)floor(index);
        double longitudeA = 0, latitudeA = 0;

        if(borneInf < tab.length-1){
            int borneSup = borneInf + 1;
            longitudeA = lerp(tab[borneInf].longitude(), tab[borneSup].longitude(), index - borneInf);
            latitudeA  = lerp(tab[borneInf].latitude(), tab[borneSup].latitude(), index - borneInf);

            return new GeoPoint(longitudeA, latitudeA);
        }else{
            return tab[borneInf];
        }
    }

    /**
     * Methode retournant la pente du terrain a la position donnee du profil
     * @param x double position
     * @return double la pente du terrain
     * @throws IllegalArgumentException  si la position n'est pas dans les bornes du profil
     */
    public double slopeAt(double x){
        isIn(x, length);

        return elevation.slopeAt(positionAt(x));
    }

    /**
     * Methode privee pour tester si la position est comprise dans les bornes du profil
     * @param position double position
     * @param longueur double longueur
     * @throws IllegalArgumentException si la position n'est pas dans les bornes
     */
    private void isIn (double position, double longueur){
        checkArgument(0 <= position && position <= longueur, "Position en dehors de ElevationProfile");
    }
}
