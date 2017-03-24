package ch.epfl.alpano;

import static ch.epfl.alpano.Azimuth.isCanonical;
import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static ch.epfl.alpano.Math2.PI2;
import static ch.epfl.alpano.Azimuth.canonicalize;

/**
 * Classe representant les parametres necessaires au dessin d'un panorama
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */


final public class PanoramaParameters {
    
    private final GeoPoint OBSERVER_POSITION;
   
    private final int OBSERVER_ELEVATION;
    private final double CENTER_AZIMUTH;
    private final double HORIZONTAL_FIELD_OF_VIEW;
    private final int MAX_DISTANCE;
    private final int WIDTH;
    private final int HEIGHT;
    private final double DELTA;
    
    /**
     * Constructeur d'un PanoramaParameters
     * 
     * @param observerPosition GeoPoint position de l'observateur
     * @param observerElevation int altitude de l'observateur
     * @param centerAzimuth double azimut du centre du panorama
     * @param horizontalFieldOfView double angle de vue horizontal
     * @param maxDistance int distance maximale de visibilité
     * @param width int largeur du panorama
     * @param height int hauteur du panorama
     */
    public PanoramaParameters(GeoPoint observerPosition, int observerElevation, double centerAzimuth, double horizontalFieldOfView, int maxDistance, int width, int height){
        OBSERVER_POSITION = requireNonNull(observerPosition);
        checkArgument(isCanonical(centerAzimuth));
        checkArgument(0 < horizontalFieldOfView && horizontalFieldOfView <= PI2);
        checkArgument(maxDistance > 0 && width > 0 && height > 0);

        OBSERVER_ELEVATION = observerElevation;
        CENTER_AZIMUTH = centerAzimuth;
        HORIZONTAL_FIELD_OF_VIEW = horizontalFieldOfView;
        MAX_DISTANCE = maxDistance;
        WIDTH = width;
        HEIGHT = height;
        
        DELTA = HORIZONTAL_FIELD_OF_VIEW/(WIDTH-1);
    }
    
    /**
     * Accesseur public 
     * 
     * @return GeoPoint position de l'observateur
     */
    public GeoPoint observerPosition (){
        return OBSERVER_POSITION;
    }
    
    /**
     * Accesseur public
     * 
     * @return int altitude de l'observateur
     */
    public int observerElevation(){
        return OBSERVER_ELEVATION;
    }
    
    /**
     * Accesseur public
     * 
     * @return double azimut du centre du panorama
     */
    public double centerAzimuth(){
        return CENTER_AZIMUTH;
    }
    
    /**
     * Accesseur public
     * 
     * @return double angle de vue horizontal
     */
    public double horizontalFieldOfView(){
        return HORIZONTAL_FIELD_OF_VIEW;
    }
    
    /**
     * Accesseur public
     * 
     * @return int distance maximale de visibilité
     */
    public int maxDistance(){
        return MAX_DISTANCE;
    }
    
    /**
     * Accesseur public
     * 
     * @return int largeur du panorama
     */
    public int width(){
        return WIDTH;
    }
    
    /**
     * Accesseur public
     * 
     * @return int hauteur du panorama
     */
    public int height(){
        return HEIGHT;
    }
    
    /**
     * Accesseur public
     * 
     * @return double angle de vue vertical
     */
    public double verticalFieldOfView(){
        return HORIZONTAL_FIELD_OF_VIEW *(HEIGHT-1)/(WIDTH-1);
    }
    
    /**
     * Methode retournant l'azimut correspondant a l'index de pixel horizontal x
     * 
     * @param x double index de pixel horizontal
     * @return double azimut correspondant a x
     * @throws IllegalArgumentException si l'index est plus petit que 0 ou plus grand que la largeur moins 1
     */
    public double azimuthForX(double x){
        checkArgument(x >= 0 && x <= (WIDTH -1));
        return  canonicalize(CENTER_AZIMUTH+(DELTA*x)-HORIZONTAL_FIELD_OF_VIEW/2); 
    }
    
    /**
     * Methode retournant l'index de pixel horizontal correspondant a l'azimut donne
     * 
     * @param a double azimut
     * @return double index de pixel
     * @throws IllegalArgumentException si l'azimut n'appartient pas à la zone visible
     */
    public double xForAzimuth(double a){
        double alpha =Math2.angularDistance(CENTER_AZIMUTH,a );
        checkArgument(Math.abs(alpha) <=HORIZONTAL_FIELD_OF_VIEW/2.);
        
        return alpha/DELTA+(WIDTH-1)/2.0;
    }
    
    /**
     * Methode retourne l'elevation correspondant à l'index de pixel vertical y
     * 
     * @param y double index de pixel vertical
     * @return double elevation
     * @throws IllegalArgumentException si l'index est inferieur a zero, ou superieur a la hauteur moins un
     */
    public double altitudeForY(double y){
        checkArgument(y >= 0 && y <= (HEIGHT-1));
        return (verticalFieldOfView()/2) - (DELTA*y);
    }
    
    /**
     * Methode retourne l'index de pixel vertical correspondant a l'elevation donnee
     * 
     * @param a double azimut
     * @return double index de pixel vertical  
     * @throws IllegalArgumentException si l'elevation n'appartient pas a la zone visible
     */
    public double yForAltitude(double a){
        checkArgument(a <= verticalFieldOfView()/2 && a >= -verticalFieldOfView()/2);
        double y0 = verticalFieldOfView()/(2*DELTA);
        return y0 + a/DELTA*(-1);
        
    }
    
    /**
     * Methode retournant vrai si et seulement si l'index passe est un index de pixel valide
     * 
     * @param x int composante x de l'index
     * @param y int composante y de l'index
     * @return boolean vrai ssi l'index passe est un index valide
     */
    boolean isValidSampleIndex(int x, int y){
        if(x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT ){
            return true;
        }else
            return false;
    }
    
    /**
     * Methode retournant l'index lineaire du pixel d'index donne
     * 
     * @param x int composante x de l'index
     * @param y int composante y de l'index
     * @return int index lineaire
     */
    int linearSampleIndex(int x, int y){
        return x + WIDTH*y;
    }

}
