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
     * @param c int constante a ajouter a la valeur
     * @return ChannelPainter avec 
     */
    default ChannelPainter add(float c){
        return (x, y) -> valueAt(x, y) + c;
    }
    
    /**
     * @param c
     * @return
     */
    default ChannelPainter sub(float c){
        return (x, y) -> valueAt(x, y) - c;
    }
    
    /**
     * @param c
     * @return
     */
    default ChannelPainter mul(float c){
        return (x, y) -> valueAt(x, y) * c;
    }
    
    /**
     * @param c
     * @return
     */
    default ChannelPainter div(float c){
        return (x, y) -> valueAt(x, y) / c;        
    }
    
    /**
     * @param op
     * @return
     */
    default ChannelPainter map(DoubleUnaryOperator op){
        return (x, y) -> (float)op.applyAsDouble(valueAt(x, y));
    }
    
    /**
     * @return
     */
    default ChannelPainter inverted(){
        return (x, y) -> 1 - valueAt(x, y);
    }
    
    /**
     * @return
     */
    default ChannelPainter clamped(){
        return (x, y) -> max(0 , min(valueAt(x, y), 1));
    }
    
    /**
     * @return
     */
    default ChannelPainter cycling(){
        return (x, y) -> (float)floorMod(valueAt(x, y), 1);
    }
    
}
