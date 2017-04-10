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
    float valueAt(int x, int y);
    
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
    
    default ChannelPainter add(int c){
        return (x, y) -> valueAt(x, y) + c;
    }
    
    default ChannelPainter sub(int c){
        return (x, y) -> valueAt(x, y) - c;
    }
    
    default ChannelPainter mul(int c){
        return (x, y) -> valueAt(x, y) * c;
    }
    
    default ChannelPainter div(int c){
        return (x, y) -> valueAt(x, y) / c;        
    }
    
    default ChannelPainter map(DoubleUnaryOperator op){
        return (x, y) -> (float)op.applyAsDouble(valueAt(x, y));
    }
    
    default ChannelPainter inverted(){
        return (x, y) -> 1 - valueAt(x, y);
    }
    
    default ChannelPainter clamped(){
        return (x, y) -> max(0 , min(valueAt(x, y), 1));
    }
    
    default ChannelPainter cycling(){
        return (x, y) -> (float)floorMod(valueAt(x, y), 1);
    }
    
}
