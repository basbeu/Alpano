package ch.epfl.alpano;

/**
*méthode pour convertir des distances à la surface de la Terre de radians en mètre et inversément
*
* @author Philippine Favre (258854)
* @author Bastien Beuchat  (257117)
*/

public interface Distance {
    double EARTH_RADIUS = 6371000;
  
    
    /**
     * @param distanceInMetres valeur en mètre à convertir en radian
     * @return distance exprimée en radian
     */
    public static double toRadians (double distanceInMetres){
        return distanceInMetres / EARTH_RADIUS;
    }
    
    /**
     * @param distanceInRadians valeur en radian à convertir en mètre
     * @return distance exprimée en mètre
     */
    public static double toMeters (double distanceInRadians) {
        return distanceInRadians * EARTH_RADIUS;
    }

}
