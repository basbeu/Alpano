package ch.epfl.alpano.gui;

/**
 * 
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
@FunctionalInterface
public interface ChannelPainter {
    float valueAt(int x, int y);
    
}
