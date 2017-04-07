package ch.epfl.alpano.gui;

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
    abstract float valueAt(int x, int y);
    
    static ChannelPainter maxDistanceToNeighbors(Panorama panorama){
        return null;
        
    }
    
    default void add(int c){
        
    }
    
    default void sub(int c){
        
    }
    
    default void mul(int c){
        
    }
    
    default void div(int c){
        
    }
    
    default void map(DoubleUnaryOperator op){
        
    }
    
    default void inverted(){
        
    }
    
    default void clamped(){
        
    }
    
    default void cycling(){
        
    }
    
}
