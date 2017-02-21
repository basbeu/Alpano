package ch.epfl.alpano;

/**
*methodes pour convertir des distances a la surface de la Terre de radians en metre et inversement
*
* @author Philippine Favre (258854)
* @author Bastien Beuchat  (257117)
*/

public interface Distance {
    double EARTH_RADIUS = 6371000;
  
    
    /**methode pour convertire des metres en radians
     * @param distanceInMetres valeur en metre a convertir en radian
     * @return distance exprimee en radian
     */
    public static double toRadians (double distanceInMetres){
        return distanceInMetres / EARTH_RADIUS;
    }
    
    /**methode pour convertir des radians en metres
     * @param distanceInRadians valeur en radian a convertir en metre
     * @return distance exprimee en metre
     */
    public static double toMeters (double distanceInRadians) {
        return distanceInRadians * EARTH_RADIUS;
    }

}
