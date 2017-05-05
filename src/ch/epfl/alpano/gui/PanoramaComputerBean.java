package ch.epfl.alpano.gui;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.unmodifiableObservableList;
import static ch.epfl.alpano.gui.PanoramaRenderer.renderPanorama;

import java.util.List;

import ch.epfl.alpano.Panorama;
import ch.epfl.alpano.PanoramaComputer;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.summit.Summit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.collections.*;

/**
 * Classe public et c'est un bean JavaFX doté de quatre propriétés : le panorama, ses paramètres (utilisateur), son image et ses étiquettes
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

public class PanoramaComputerBean {
    
    private PanoramaComputer panoramaComputer;
    private Labelizer labelizer;
    private ObjectProperty<Panorama> panorama;
    private ObjectProperty<PanoramaUserParameters> parameters;
    private ObjectProperty<Image> image;
    private ObjectProperty<ObservableList<Node>> labels;
    
    public PanoramaComputerBean(ContinuousElevationModel cDem, List<Summit> summits){
      parameters = new SimpleObjectProperty<>();
      parameters.addListener((b,o,n)-> compute());
      panoramaComputer = new PanoramaComputer(cDem);
      labelizer = new Labelizer(cDem, summits);
      labels = new SimpleObjectProperty<>(observableArrayList());
      panorama = new SimpleObjectProperty<>();
      image = new SimpleObjectProperty<>();
    }
    
    public ObjectProperty<PanoramaUserParameters> parametersProperty(){
        return parameters;
    }
    
    public PanoramaUserParameters getParameters(){
        return parameters.getValue();
    }
    
    public void setParameters(PanoramaUserParameters newParameters){
        parameters.setValue(newParameters);
    }
    
    public ReadOnlyObjectProperty<Panorama> panoramaProperty(){
        return panorama;
    }
    
    public Panorama getPanorama(){
        return panorama.getValue();
    }
    
    public ReadOnlyObjectProperty<Image> imageProperty(){
        return image;
    }
    
    public Image getImage(){
        return image.getValue();
    }
    
    public ReadOnlyObjectProperty<ObservableList<Node>> labelsProperty(){
        return labels;
    }
    
    public ObservableList<Node> getLabels(){
        return unmodifiableObservableList(labels.getValue());
    }
    
    private void compute(){
        panorama.setValue(panoramaComputer.computePanorama(parameters.getValue().panoramaParameters()));
        
        labels.getValue().setAll(labelizer.labels(parameters.getValue().panoramaParameters()));
        
        ChannelPainter distance = panorama.getValue()::distanceAt;
        ChannelPainter opacity = distance.map(d -> d == Float.POSITIVE_INFINITY ? 0 : 1);
        
        ChannelPainter h = (x,y)->360*distance.div(100000).cycling().valueAt(x, y);

        ChannelPainter s = distance.div(200000).clamped().inverted();

        ChannelPainter slope = panorama.getValue()::slopeAt;
        ChannelPainter b = (x,y)->0.3f+0.7f*slope.mul(2).div((float)Math.PI).inverted().valueAt(x, y);

        ImagePainter painter = ImagePainter.hsb(h, s, b, opacity);
        
        image.setValue(renderPanorama(panorama.getValue(), painter));
    }

}
