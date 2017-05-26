package ch.epfl.alpano.gui;

import static ch.epfl.alpano.gui.PanoramaRenderer.renderPanorama;
import static javafx.collections.FXCollections.observableArrayList;

import java.util.List;

import ch.epfl.alpano.Panorama;
import ch.epfl.alpano.PanoramaComputer;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.summit.Summit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * Classe public et c'est un bean JavaFX doté de quatre propriétés : le panorama, ses paramètres (utilisateur), son image et ses étiquettes
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

public final class PanoramaComputerBean {

    private final PanoramaComputer panoramaComputer;
    private final Labelizer labelizer;
    private final ObjectProperty<Panorama> panorama;
    private final ObjectProperty<PanoramaUserParameters> parameters;
    private final ObjectProperty<Image> image;
    private final ObservableList<Node> labels;

    /**
     * Constructeur d'un PanoramaComputerBean
     * @param cDem ContinuousElevationModel modèle pour créer le panoramaComputer ainsi que le labelizer
     * @param summits List<Summit> liste des sommets
     */
    public PanoramaComputerBean(ContinuousElevationModel cDem, List<Summit> summits){
        parameters = new SimpleObjectProperty<>();
        parameters.addListener((b,o,n)->compute());
        panoramaComputer = new PanoramaComputer(cDem);
        labelizer = new Labelizer(cDem, summits);
        labels = observableArrayList();
        panorama = new SimpleObjectProperty<>();
        image = new SimpleObjectProperty<>();
    }

    /**
     * Accesseur public des paramètres du panorama
     * @return ObjectProperty<PanoramaUserParameters> obtenir la propriété JavaFX correspondant aux paramètres
     */
    public ObjectProperty<PanoramaUserParameters> parametersProperty(){
        return parameters;
    }

    /**
     * Accesseur public des paramètres du panorama
     * @return PanoramaUserParameters paramètres du panorama
     */
    public PanoramaUserParameters getParameters(){
        return parameters.getValue();
    }

    /**
     * Modificateur public des paramètres du panorama
     * @param newParameters PanoramaUserParameters nouveaux paramètres
     */
    public void setParameters(PanoramaUserParameters newParameters){
        parameters.setValue(newParameters);
    }

    /**
     * Accesseur public du panorama
     * @return ReadOnlyObjectProperty<Panorama> panorama accessible en lecture seule
     */
    public ReadOnlyObjectProperty<Panorama> panoramaProperty(){
        return panorama;
    }

    /**
     * Accesseur public du panorama
     * @return Panorama le panorama
     */
    public Panorama getPanorama(){
        return panorama.getValue();
    }

    /**
     * Accesseur public de l'image
     * @return ReadOnlyObjectProperty<Image> l'image accessible en lecture seule
     */
    public ReadOnlyObjectProperty<Image> imageProperty(){
        return image;
    }

    /**
     * Accesseur public de l'image
     * @return Image l'image
     */
    public Image getImage(){
        return image.getValue();
    }

    /**
     * Accesseur public des étiquettes
     * @return ObservableList<Node> liste observable JavaFX avec les étiquettes
     */
    public ObservableList<Node> getLabels(){
        return labels;
    }

    /**
     * Méthode privée effectuant le recalcul de l'image du panorama
     */
    private void compute(){
        //Mise à jour du panorama et des étiquettes
        panorama.setValue(panoramaComputer.computePanorama(parameters.getValue().panoramaParameters()));

        labels.setAll(labelizer.labels(parameters.getValue().panoramaDisplayParameters()));

        //Re-calcul des canaux, formules données dans l'énoncé
        ChannelPainter distance = panorama.getValue()::distanceAt;
        ChannelPainter opacity = distance.map((d)->d == Float.POSITIVE_INFINITY ? 0 : 1);

        ChannelPainter h = (x,y)->360*distance.div(100_000).cycling().valueAt(x, y);

        ChannelPainter s = distance.div(200_000).clamped().inverted();

        ChannelPainter slope = panorama.getValue()::slopeAt;
        ChannelPainter b = (x,y)->0.3f+0.7f*slope.mul(2).div((float)Math.PI).inverted().valueAt(x, y);

        ImagePainter painter = ImagePainter.hsb(h, s, b, opacity);

        //Nouvelle image
        image.setValue(renderPanorama(panorama.getValue(), painter));
    }

}
