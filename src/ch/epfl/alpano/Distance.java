package ch.epfl.alpano;

/**
 *Interface fournissant des methodes pour convertir des distances a la surface de la Terre de radians en metre et inversement
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

public interface Distance {
    double EARTH_RADIUS = 6371000;


    /**
     * Convertir des metres en radians
     * @param distanceInMetres double en metres a convertir en radians
     * @return double distance exprimee en radian
     */
    static double toRadians (double distanceInMetres){
        return distanceInMetres / EARTH_RADIUS;
    }

    /**
     * Convertir des radians en metres
     * @param distanceInRadians double en radians a convertir en metres
     * @return double distance exprimee en metre
     */
    static double toMeters (double distanceInRadians) {
        return distanceInRadians * EARTH_RADIUS;
    }

}
