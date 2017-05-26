package ch.epfl.alpano.gui;

import static javafx.application.Platform.runLater;

import java.util.EnumMap;

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
    private ObjectProperty<PanoramaUserParameters> parameters;
    private EnumMap<UserParameter, ObjectProperty<Integer>> properties;

    /**
     * Constructeur d'un bean contenant les parametres utilisateurs d'un panorama
     * @param params PanoramaUserParameters representant les parametres du panorama
     */
    public PanoramaParametersBean(PanoramaUserParameters params){
        parameters = new SimpleObjectProperty<>(params);

        properties = new EnumMap<>(UserParameter.class);
        properties.put(UserParameter.OBSERVER_LONGITUDE, new SimpleObjectProperty<>(params.observerLongitude()));
        properties.put(UserParameter.OBSERVER_LATITUDE, new SimpleObjectProperty<>(params.observerLatitude()));
        properties.put(UserParameter.OBSERVER_ELEVATION, new SimpleObjectProperty<>(params.observerElevation()));
        properties.put(UserParameter.CENTER_AZIMUTH, new SimpleObjectProperty<>(params.centerAzimuth()));
        properties.put(UserParameter.HORIZONTAL_FIELD_OF_VIEW, new SimpleObjectProperty<>(params.horizontalFieldOfView()));
        properties.put(UserParameter.MAX_DISTANCE, new SimpleObjectProperty<>(params.maxDistance()));
        properties.put(UserParameter.WIDTH, new SimpleObjectProperty<>(params.width()));
        properties.put(UserParameter.HEIGHT, new SimpleObjectProperty<>(params.height()));
        properties.put(UserParameter.SUPER_SAMPLING_EXPONENT, new SimpleObjectProperty<>(params.superSamplingExponent()));

        properties.forEach((k,v)->v.addListener((b, o, n) -> runLater(this::synchronizeParameters)));
    }

    /**
     * @return ReadOnlyObjectProperty<PanoramaUserParameters> representant les parametres du panorama
     */
    public ReadOnlyObjectProperty<PanoramaUserParameters> parametersProperty(){
        return parameters;
    }

    /**
     * @return ObjectProperty<Integer> representant la longitude
     */
    public ObjectProperty<Integer> observerLongitudeProperty(){
        return properties.get(UserParameter.OBSERVER_LONGITUDE);
    }

    /**
     * @return ObjectProperty<Integer> representant la latitude
     */
    public ObjectProperty<Integer> observerLatitudeProperty(){
        return properties.get(UserParameter.OBSERVER_LATITUDE);
    }

    /**
     * @return ObjectProperty<Integer> representant l'altitude
     */
    public ObjectProperty<Integer> observerElevationProperty(){
        return properties.get(UserParameter.OBSERVER_ELEVATION);
    }

    /**
     * @return ObjectProperty<Integer> representant l'azimuth central
     */
    public ObjectProperty<Integer> centerAzimuthProperty(){
        return properties.get(UserParameter.CENTER_AZIMUTH);
    }

    /**
     * @return ObjectProperty<Integer> representant le champ de vision horizontal
     */
    public ObjectProperty<Integer> horizontalFieldOfViewProperty(){
        return properties.get(UserParameter.HORIZONTAL_FIELD_OF_VIEW);
    }

    /**
     * @return ObjectProperty<Integer> representant la distance maximale
     */
    public ObjectProperty<Integer> maxDistanceProperty(){
        return properties.get(UserParameter.MAX_DISTANCE);
    }

    /**
     * @return ObjectProperty<Integer> representant la largeur
     */
    public ObjectProperty<Integer> widthProperty(){
        return properties.get(UserParameter.WIDTH);
    }

    /**
     * @return ObjectProperty<Integer> representant la hauteur
     */
    public ObjectProperty<Integer> heightProperty(){
        return properties.get(UserParameter.HEIGHT);
    }

    /**
     * @return ObjectProperty<Integer> representant l'exposant de super-echantillonnage
     */
    public ObjectProperty<Integer> superSamplingExponentProperty(){
        return properties.get(UserParameter.SUPER_SAMPLING_EXPONENT);
    }

    /**
     * Methode privee synchronisant les parametres et proprietes
     */
    private void synchronizeParameters(){
        PanoramaUserParameters userParam = new PanoramaUserParameters(observerLongitudeProperty().getValue(), observerLatitudeProperty().getValue(), observerElevationProperty().getValue(), centerAzimuthProperty().getValue(), horizontalFieldOfViewProperty().getValue(), maxDistanceProperty().getValue(), widthProperty().getValue(), heightProperty().getValue(), superSamplingExponentProperty().getValue());
        parameters.setValue(userParam);        

        observerLongitudeProperty().setValue(userParam.observerLongitude());
        observerLatitudeProperty().setValue(userParam.observerLatitude());
        observerElevationProperty().setValue(userParam.observerElevation());
        centerAzimuthProperty().setValue(userParam.centerAzimuth());
        horizontalFieldOfViewProperty().setValue(userParam.horizontalFieldOfView());
        maxDistanceProperty().setValue(userParam.maxDistance());
        widthProperty().setValue(userParam.width());
        heightProperty().setValue(userParam.height());
        superSamplingExponentProperty().setValue(userParam.superSamplingExponent());
    }

}
