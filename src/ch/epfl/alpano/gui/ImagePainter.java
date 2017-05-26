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
    /**
     * Donne la couleur de l'image pour un pixel donne
     * @param x int coordonnee x du pixel
     * @param y int coordonnee y du pixel
     * @return Color (javafx.scene.paint.Color) representant la couleur du pixel
     */
    Color colorAt(int x,int y);

    /**
     * Retourne un peintre HSB
     * @param h ChannelPainter representant le canal de la teinte (hue)
     * @param s ChannelPainter representant le canal de la saturation
     * @param b ChannelPainter representant le canal de la luminosité
     * @param o ChannelPainter representant le canal de l'opacité
     * @return ImagePainter representant un peintre HSB
     */
    static ImagePainter hsb(ChannelPainter h, ChannelPainter s, ChannelPainter b, ChannelPainter o){
        return (x,y)->Color.hsb(h.valueAt(x, y), s.valueAt(x, y), b.valueAt(x, y), o.valueAt(x, y));
    }

    /**
     * Retourne un peintre en niveau de gris
     * @param g ChannelPainter representant le canal de gris
     * @param o ChannelPainter representant le canal d'opacité
     * @return ImagePainter representant un peintre en niveau de gris
     */
    static ImagePainter gray(ChannelPainter g, ChannelPainter o){
        return (x,y)->Color.gray(g.valueAt(x, y), o.valueAt(x, y));
    }
}
