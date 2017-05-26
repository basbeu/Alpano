package ch.epfl.alpano.gui;

import ch.epfl.alpano.Panorama;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Interface permettant d'obtenir une image d'un panorama
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public interface PanoramaRenderer {

    /**
     * Crée l'image d'un panorama
     * @param panorama Panorama representant le panorama a peindre
     * @param painter ImagePainter representant la maniere de peindre le panorama
     * @return Image representant l'image du panorama selon le peintre
     */
    static Image renderPanorama(Panorama panorama, ImagePainter painter){
        WritableImage image = new WritableImage(panorama.parameters().width(), panorama.parameters().height());
        PixelWriter writer = image.getPixelWriter();

        for(int x = 0; x < image.getWidth(); ++x){
            for(int y = 0; y < image.getHeight(); ++y){
                writer.setColor(x, y, painter.colorAt(x, y));
            }
        }

        return image;
    }
}
