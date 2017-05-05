package ch.epfl.alpano.gui;

import static javafx.application.Platform.runLater;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Classe public et c'est un bean JavaFX contenant les paramètres (utilisateur) du panorama 
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

public class PanoramaParametersBean {
    
    //faire une enummap pour les attributs
    private ReadOnlyObjectProperty<PanoramaUserParameters> parameters;
    private ObjectProperty<Integer> observerLongitude;
    private ObjectProperty<Integer> observerLatitude;
    private ObjectProperty<Integer> observerElevation;
    private ObjectProperty<Integer> centerAzimuth;
    private ObjectProperty<Integer> horizontalFieldOfView;
    private ObjectProperty<Integer> maxDistance;
    private ObjectProperty<Integer> width;
    private ObjectProperty<Integer> heigth;
    private ObjectProperty<Integer> superSamplingExponent;
    
    public PanoramaParametersBean(PanoramaUserParameters params){
        parameters = new SimpleObjectProperty<>(params);
        
        observerLongitude = new SimpleObjectProperty<>(params.observerLongitude());
        observerLatitude = new SimpleObjectProperty<>(params.observerLatitude());
        observerElevation = new SimpleObjectProperty<>(params.observerElevation());
        centerAzimuth = new SimpleObjectProperty<>(params.centerAzimuth());
        horizontalFieldOfView = new SimpleObjectProperty<>(params.horizontalFieldOfView());
        maxDistance = new SimpleObjectProperty<>(params.maxDistance());
        width = new SimpleObjectProperty<>(params.width());
        heigth = new SimpleObjectProperty<>(params.height());
        superSamplingExponent = new SimpleObjectProperty<>(params.superSamplingExponent());
        
        observerLongitude.addListener((b, o, n) -> runLater(this::synchronizeParameters));
        observerLatitude.addListener((b, o, n) -> runLater(this::synchronizeParameters));
        observerElevation.addListener((b, o, n) -> runLater(this::synchronizeParameters));
        centerAzimuth.addListener((b, o, n) -> runLater(this::synchronizeParameters));
        horizontalFieldOfView.addListener((b, o, n) -> runLater(this::synchronizeParameters));
        maxDistance.addListener((b, o, n) -> runLater(this::synchronizeParameters));
        width.addListener((b, o, n) -> runLater(this::synchronizeParameters));
        heigth.addListener((b, o, n) -> runLater(this::synchronizeParameters));
        superSamplingExponent.addListener((b, o, n) -> runLater(this::synchronizeParameters));
        
    }
    
    public ReadOnlyObjectProperty<PanoramaUserParameters> parametersProperty(){
        return parameters;
    }
    
    public ObjectProperty<Integer> observerLongitudeProperty(){
        return observerLongitude;
    }
    
    public ObjectProperty<Integer> observerLatitudeProperty(){
        return observerLatitude;
    }
    
    public ObjectProperty<Integer> observerElevationProperty(){
        return observerElevation;
    }
    
    public ObjectProperty<Integer> centerAzimuthProperty(){
        return centerAzimuth;
    }
    
    public ObjectProperty<Integer> horizontalFieldOfViewProperty(){
        return horizontalFieldOfView;
    }
    
    public ObjectProperty<Integer> maxDistanceProperty(){
        return maxDistance;
    }
    
    public ObjectProperty<Integer> widthProperty(){
        return width;
    }
    
    public ObjectProperty<Integer> heightProperty(){
        return heigth;
    }
    
    public ObjectProperty<Integer> superSamplingExponentProperty(){
        return superSamplingExponent;
    }
    
    private void synchronizeParameters(){
        PanoramaUserParameters userParam = new PanoramaUserParameters(observerLongitude.getValue(), observerLatitude.getValue(), observerElevation.getValue(), centerAzimuth.getValue(), horizontalFieldOfView.getValue(), maxDistance.getValue(), width.getValue(), heigth.getValue(), superSamplingExponent.getValue());
        parameters = new SimpleObjectProperty<>(userParam);
        
        observerLongitude.setValue(userParam.observerLongitude());
        observerLatitude.setValue(userParam.observerLatitude());
        observerElevation.setValue(userParam.observerElevation());
        centerAzimuth.setValue(userParam.centerAzimuth());
        horizontalFieldOfView.setValue(userParam.horizontalFieldOfView());
        maxDistance.setValue(userParam.maxDistance());
        width.setValue(userParam.width());
        heigth.setValue(userParam.height());
        superSamplingExponent.setValue(userParam.superSamplingExponent());
    }
    
}
