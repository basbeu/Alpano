package ch.epfl.alpano;

import static ch.epfl.alpano.Azimuth.canonicalize;
import static ch.epfl.alpano.Azimuth.isCanonical;
import static ch.epfl.alpano.Math2.PI2;
import static ch.epfl.alpano.Math2.angularDistance;
import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.lang.Math.abs;
import static java.util.Objects.requireNonNull;

/**
 * Classe representant les parametres necessaires au dessin d'un panorama
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */


final public class PanoramaParameters {

    private final GeoPoint observerPosition;
    private final int observerElevation;
    private final double centerAzimuth;
    private final double horizontalFieldOfView;
    private final int maxDistance;
    private final int width;
    private final int height;
    private final double delta;

    /**
     * Constructeur d'un PanoramaParameters
     * @param observerPosition GeoPoint position de l'observateur
     * @param observerElevation int altitude de l'observateur
     * @param centerAzimuth double azimut du centre du panorama
     * @param horizontalFieldOfView double angle de vue horizontal
     * @param maxDistance int distance maximale de visibilité
     * @param width int largeur du panorama
     * @param height int hauteur du panorama
     * @throws  NullPointerException si la position de l'observateur est null
     *          IllegalArgumentException si l'azimut central n'est pas canonique
     *          IllegalArgumentException si l'angle de vue horizontal n'est pas compris entre ]0;2PI]
     *          IllegalArgumentException si la largeur, hauteur et distance maximale ne sont pas strictement positives
     *          
     * 
     */
    public PanoramaParameters(GeoPoint observerPosition, int observerElevation, double centerAzimuth, double horizontalFieldOfView, int maxDistance, int width, int height){
        this.observerPosition = requireNonNull(observerPosition, "La position de l'observation ne doit pas être null!");
        checkArgument(isCanonical(centerAzimuth), "L'angle n'est pas canonique!");
        checkArgument(0 < horizontalFieldOfView && horizontalFieldOfView <= PI2, "L'angle de vue horizontal n'est pas compris entre 0 (exclu) et 2PI (inclu)!");
        checkArgument(maxDistance > 0 && width > 0 && height > 0, "La largeur, la hauteur et la distance maximale doivent être strictement positives!");

        this.observerElevation = observerElevation;
        this.centerAzimuth = centerAzimuth;
        this.horizontalFieldOfView = horizontalFieldOfView;
        this.maxDistance = maxDistance;
        this.width = width;
        this.height = height;

        delta = horizontalFieldOfView / (width - 1);
    }

    /**
     * Accesseur public 
     * @return GeoPoint position de l'observateur
     */
    public GeoPoint observerPosition (){
        return observerPosition;
    }

    /**
     * Accesseur public
     * @return int altitude de l'observateur
     */
    public int observerElevation(){
        return observerElevation;
    }

    /**
     * Accesseur public
     * @return double azimut du centre du panorama
     */
    public double centerAzimuth(){
        return centerAzimuth;
    }

    /**
     * Accesseur public
     * @return double angle de vue horizontal
     */
    public double horizontalFieldOfView(){
        return horizontalFieldOfView;
    }

    /**
     * Accesseur public
     * @return int distance maximale de visibilité
     */
    public int maxDistance(){
        return maxDistance;
    }

    /**
     * Accesseur public
     * @return int largeur du panorama
     */
    public int width(){
        return width;
    }

    /**
     * Accesseur public
     * @return int hauteur du panorama
     */
    public int height(){
        return height;
    }

    /**
     * Accesseur public
     * @return double angle de vue vertical
     */
    public double verticalFieldOfView(){
        return horizontalFieldOfView() * (height() - 1) / (width() - 1);
    }

    /**
     * Methode retournant l'azimut correspondant a l'index de pixel horizontal x
     * @param x double index de pixel horizontal
     * @return double azimut correspondant a x
     * @throws IllegalArgumentException si l'index est plus petit que 0 ou plus grand que la largeur moins 1
     */
    public double azimuthForX(double x){
        checkArgument(x >= 0 && x <= (width() - 1));
        return  canonicalize(centerAzimuth() + (delta * x) - horizontalFieldOfView() / 2); 
    }

    /**
     * Methode retournant l'index de pixel horizontal correspondant a l'azimut donne
     * @param a double azimut
     * @return double index de pixel
     * @throws IllegalArgumentException si l'azimut n'appartient pas à la zone visible
     */
    public double xForAzimuth(double a){
        double alpha = angularDistance(centerAzimuth(), a);
        checkArgument(abs(alpha) <= horizontalFieldOfView() / 2., "L'azimut n'appartient pas à la zone visible!");

        return alpha / delta + (width() - 1) / 2.0;
    }

    /**
     * Methode retourne l'elevation correspondant à l'index de pixel vertical y
     * @param y double index de pixel vertical
     * @return double elevation
     * @throws IllegalArgumentException si l'index est inferieur a zero, ou superieur a la hauteur moins un
     */
    public double altitudeForY(double y){
        checkArgument(y >= 0 && y <= (height() - 1), "L'index est soit inférieur à zéro, soit supérieur à la hauteur moins un!");
        return (verticalFieldOfView() / 2) - (delta * y);
    }

    /**
     * Methode retourne l'index de pixel vertical correspondant a l'elevation donnee
     * @param a double azimut
     * @return double index de pixel vertical  
     * @throws IllegalArgumentException si l'elevation n'appartient pas a la zone visible
     */
    public double yForAltitude(double a){
        checkArgument(a <= verticalFieldOfView() / 2 && a >= -verticalFieldOfView() / 2, "L'élévation n'appartient pas à la zone visible!");
        double y0 = verticalFieldOfView() / (2 * delta);
        return y0 + a / delta * (-1);

    }

    /**
     * Methode retournant vrai si et seulement si l'index passe est un index de pixel valide
     * @param x int composante x de l'index
     * @param y int composante y de l'index
     * @return boolean vrai ssi l'index passe est un index valide
     */
    boolean isValidSampleIndex(int x, int y){
        if(x >= 0 && x < width() && y >= 0 && y < height()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Methode retournant l'index lineaire du pixel d'index donne
     * @param x int composante x de l'index
     * @param y int composante y de l'index
     * @return int index lineaire
     */
    int linearSampleIndex(int x, int y){
        return x + width() * y;
    }

}
