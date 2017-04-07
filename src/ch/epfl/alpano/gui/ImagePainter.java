package ch.epfl.alpano.gui;

import javafx.scene.paint.Color;

/**
 * Interface fonctionnelle representant un peintre d'image
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
@FunctionalInterface
public interface ImagePainter {
    Color colorAt(int x,int y);
    
    static ImagePainter hsb(ChannelPainter h, ChannelPainter s, ChannelPainter b, ChannelPainter o){
        return null;
    }
    
    static ImagePainter gray(ChannelPainter g, ChannelPainter o){
        return null;
    }
}
