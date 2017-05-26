package ch.epfl.alpano.gui;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static ch.epfl.alpano.Math2.floorMod;

import java.util.function.DoubleUnaryOperator;

import ch.epfl.alpano.Panorama;

/**
 * Interface fonctionnelle représentant un peintre de canal
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
@FunctionalInterface
public interface ChannelPainter {


    /**
     * Methode abstraite retournant la valeur du canal en ce point
     * @param x int coordonnee entiere x d'un point 
     * @param y int coordonnee entiere y d'un point
     * @return float valeur du canal en ce point
     */
    float valueAt(int x, int y);

    /**
     * Methode static pour calculer la difference de distance entre un point et le plus lointain de ses voisins 
     * @param panorama Panorama à parcourir
     * @return ChannelPainter dont la valeur pour un point est la difference de distance entre le plus lointain des voisins 
     *          et le point en question 
     */
    static ChannelPainter maxDistanceToNeighbors(Panorama panorama){


        return (x,y) -> {
            float[] distance = new float[4];
            distance[0] = panorama.distanceAt(x - 1, y, 0);
            distance [1] = panorama.distanceAt(x + 1, y, 0);
            distance [2] = panorama.distanceAt(x, y - 1, 0);
            distance [3] = panorama.distanceAt(x, y + 1, 0);

            float max = Float.MIN_VALUE;
            for(int i = 0; i < distance.length - 1; i++){
                if (distance[i] > max){
                    max = distance[i];
                }
            }

            return max - panorama.distanceAt(x, y, 0);

        };

    }

    /**
     * Methode par default pour ajouter la valeur produite par le peintre par une constante passee en argument
     * @param c float constante a ajouter a la valeur
     * @return ChannelPainter avec c ajoute a toutes les valeurs produites
     */
    default ChannelPainter add(float c){
        return (x, y) -> valueAt(x, y) + c;
    }

    /**
     * Methode par default pour soustraire la valeur produite par le peintre par une constante passee en argument
     * @param c float constante a soustraire a la valeur
     * @return ChannelPainter avec c soustrait a toutes les valeurs produites
     */
    default ChannelPainter sub(float c){
        return (x, y) -> valueAt(x, y) - c;
    }

    /**     
     * Methode par default pour multiplier la valeur produite par le peintre par une constante passee en argument
     * @param c float constante a multiplier a la valeur
     * @return ChannelPainter avec c multiplie a toutes les valeurs produites
     */
    default ChannelPainter mul(float c){
        return (x, y) -> valueAt(x, y) * c;
    }

    /**     
     * Methode par default pour diviser la valeur produite par le peintre par une constante passee en argument
     * @param c float constante a diviser a la valeur
     * @return ChannelPainter avec toutes les valeurs produites divisees par c
     */
    default ChannelPainter div(float c){
        return (x, y) -> valueAt(x, y) / c;        
    }

    /**
     * Methode par defaut permettant d'appliquer à la valeur produite par le peintre un operateur unaire
     * @param op DoubleUnaryOperator operateur unaire a appliquer a la valeur
     * @return ChannelPainter canal avec operateur applique aux valeurs
     */
    default ChannelPainter map(DoubleUnaryOperator op){
        return (x, y) -> (float)op.applyAsDouble(valueAt(x, y));
    }

    /**
     * Fonction mathematique  f(x) = 1 - x
     * @return ChannelPainter compose avec les valeurs apres application de la fonction
     */
    default ChannelPainter inverted(){
        return (x, y) -> 1 - valueAt(x, y);
    }

    /**
     * Fonction mathematique f(x) = max(0, min(x, 1))
     * @return ChannelPainter compose avec les valeurs apres application de la fonction
     */
    default ChannelPainter clamped(){
        return (x, y) -> max(0 , min(valueAt(x, y), 1));
    }

    /**
     * Fonction mathematique f(x) = x mod 1
     * @return ChannelPainter compose avec les valeurs apres application de la fonction
     */
    default ChannelPainter cycling(){
        return (x, y) -> (float)floorMod(valueAt(x, y), 1);
    }

}
