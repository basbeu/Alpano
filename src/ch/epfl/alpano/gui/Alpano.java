package ch.epfl.alpano.gui;


import static java.lang.String.format;
import static java.lang.Math.toDegrees;
import static java.lang.Math.signum;
import static java.awt.Desktop.getDesktop;
import static ch.epfl.alpano.summit.GazetteerParser.readSummitsFrom;
import static ch.epfl.alpano.Azimuth.toOctantString;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

import ch.epfl.alpano.Panorama;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.DiscreteElevationModel;
import ch.epfl.alpano.dem.HgtDiscreteElevationModel;
import ch.epfl.alpano.summit.Summit;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public final class Alpano extends Application {
    private final static File SUMMIT_FILE = new File("alps.txt");

    private final static File HGT_FILE1 = new File("N45E006.hgt");
    private final static File HGT_FILE2 = new File("N45E007.hgt");
    private final static File HGT_FILE3 = new File("N45E008.hgt");
    private final static File HGT_FILE4 = new File("N45E009.hgt");
    private final static File HGT_FILE5 = new File("N46E006.hgt");
    private final static File HGT_FILE6 = new File("N46E007.hgt");
    private final static File HGT_FILE7 = new File("N46E008.hgt");
    private final static File HGT_FILE8 = new File("N46E009.hgt");

    private final ContinuousElevationModel cem;
    private final List<Summit> summits;
    private final PanoramaComputerBean computerBean;
    private final PanoramaParametersBean parametersBean;

    public Alpano() throws IOException{
        cem = loadHgt();
        try {
            summits = readSummitsFrom(SUMMIT_FILE);
        } catch (IOException e) {
            throw new IOException("Problème avec le fichier des sommets");
        }
        computerBean = new PanoramaComputerBean(cem, summits);
        parametersBean = new PanoramaParametersBean(PredefinedPanoramas.ALPES_JURA);
    } 

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // … création de l'interface graphique
        TextArea infos = new TextArea();
        infos.setEditable(false);
        infos.setPrefRowCount(2);

        GridPane paramsGrid = getParamsGrid(infos);
        StackPane panoPane = getPanoPane(infos);

        BorderPane root = new BorderPane(panoPane,null,null,paramsGrid,null);
        Scene scene = new Scene(root);

        primaryStage.setTitle("Alpano");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    private static ContinuousElevationModel loadHgt(){
        DiscreteElevationModel dem1 = new HgtDiscreteElevationModel(HGT_FILE1);
        DiscreteElevationModel dem2 = new HgtDiscreteElevationModel(HGT_FILE2);
        DiscreteElevationModel dem3 = new HgtDiscreteElevationModel(HGT_FILE3);
        DiscreteElevationModel dem4 = new HgtDiscreteElevationModel(HGT_FILE4);
        DiscreteElevationModel dem5 = new HgtDiscreteElevationModel(HGT_FILE5);
        DiscreteElevationModel dem6 = new HgtDiscreteElevationModel(HGT_FILE6);
        DiscreteElevationModel dem7 = new HgtDiscreteElevationModel(HGT_FILE7);
        DiscreteElevationModel dem8 = new HgtDiscreteElevationModel(HGT_FILE8);
        
        return new ContinuousElevationModel(dem1.union(dem2).union(dem3).union(dem4).union(dem5.union(dem6).union(dem7).union(dem8)));
    }

    private StackPane getPanoPane(TextArea infos){
        Text updateText = new Text("Les paramètres du panorama ont changé. Cliquez ici pour mettre le dessin à jour.");
        updateText.setFont(new Font(40));
        updateText.setTextAlignment(TextAlignment.CENTER);

        StackPane updateNotice = new StackPane(updateText);
        updateNotice.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, 0.9), CornerRadii.EMPTY, Insets.EMPTY)));
        updateNotice.visibleProperty().bind(computerBean.parametersProperty().isNotEqualTo(parametersBean.parametersProperty()));
        updateNotice.setOnMouseClicked((e)->computerBean.setParameters(parametersBean.parametersProperty().getValue()));

        ImageView panoView = new ImageView();

        panoView.imageProperty().bind(computerBean.imageProperty());
        panoView.fitWidthProperty().bind(parametersBean.widthProperty());
        panoView.preserveRatioProperty().setValue(true);
        panoView.smoothProperty().setValue(true);

        panoView.setOnMouseMoved((e)->{
            int x = (int) e.getX();
            int y = (int) e.getY();

            Panorama p = computerBean.getPanorama();
            double azimuth = computerBean.getParameters().panoramaParameters().azimuthForX(x);
            double latitude = p.latitudeAt(x, y);
            double longitude = p.longitudeAt(x, y);

            StringBuilder sb = new StringBuilder("Position : ");
            sb.append(format("%.4f °",toDegrees(latitude))).append(signum(latitude)==1?"N":"S")
            .append(format(" %.4f °", toDegrees(longitude))).append(signum(longitude)==1?"E":"W")
            .append("\nDistance : ").append(format("%.1f", p.distanceAt(x, y)/1000d))
            .append(" km\nAltitude : ").append(format("%.0f", p.elevationAt(x, y)))
            .append(" m\nAzimut : ").append(format("%.1f °", toDegrees(azimuth))).append(toOctantString(azimuth, "N", "E", "S", "W"))
            .append("\t\tElévation : ").append(format("%.1f °", toDegrees(computerBean.getParameters().panoramaParameters().altitudeForY(y))));

            infos.setText(sb.toString());
        });

        panoView.setOnMouseClicked((e)->{
            int x = (int) e.getX();
            int y = (int) e.getY();

            Panorama p = computerBean.getPanorama();
            double latitude = toDegrees(p.latitudeAt(x, y));
            double longitude = toDegrees(p.longitudeAt(x, y));

            String qy = format((Locale)null,"mlat=%.2f&mlon=%.2f", latitude, longitude);  // "query" : partie après le ?
            String fg = format((Locale)null,"map=15/%.2f/%.2f", latitude, longitude);  // "fragment" : partie après le #

            URI osmURI;
            try {
                osmURI = new URI("http", "www.openstreetmap.org", "/", qy, fg);
                getDesktop().browse(osmURI);
            } catch (URISyntaxException exception) {
                exception.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });


        Pane labelsPane = new Pane();
        Bindings.bindContent(labelsPane.getChildren(),computerBean.getLabels());
        labelsPane.setMouseTransparent(true);
        StackPane panoGroup = new StackPane(panoView, labelsPane);
        ScrollPane panoScrollPane = new ScrollPane(panoGroup);

        return new StackPane(panoScrollPane, updateNotice);
    }

    private GridPane getParamsGrid(TextArea infos){
        Label laLatitude = new Label("Latitude (°) :");
        Label laLongitude = new Label("Longitude (°) :");
        Label laElevation = new Label("Altitude (m) :");
        Label laAzimuth = new Label("Azimut (°) :");
        Label laHorizontalFieldOfView = new Label("Angle de vue (°) :");
        Label laMaxDistance = new Label("Visibilité (km) :");
        Label laWidth = new Label("Largeur (px) :");
        Label laHeight = new Label("Hauteur (px) :");
        Label laSuperSamplingExponent = new Label("Suréchantillonnage :");

        TextField tfLatitude = buildTextField(parametersBean.observerLatitudeProperty(), 7, 4);
        TextField tfLongitude = buildTextField(parametersBean.observerLongitudeProperty(), 7, 4);
        TextField tfElevation = buildTextField(parametersBean.observerElevationProperty(), 4, 0);
        TextField tfAzimuth = buildTextField(parametersBean.centerAzimuthProperty(), 3, 0);
        TextField tfHorizontalFieldOfView = buildTextField(parametersBean.horizontalFieldOfViewProperty(), 3, 0);
        TextField tfMaxDistance = buildTextField(parametersBean.maxDistanceProperty(), 3, 0);
        TextField tfWidth = buildTextField(parametersBean.widthProperty(), 4, 0);
        TextField tfHeight = buildTextField(parametersBean.heightProperty(), 4, 0);

        StringConverter<Integer> stringConverter = new LabeledListStringConverter("non", "2x", "4x");

        ChoiceBox<Integer> cbSuperSamplingExponent = new ChoiceBox<>();
        cbSuperSamplingExponent.getItems().addAll(0,1,2);
        cbSuperSamplingExponent.valueProperty().bindBidirectional(parametersBean.superSamplingExponentProperty());
        cbSuperSamplingExponent.setConverter(stringConverter);

        GridPane paramsGrid = new GridPane();

        paramsGrid.addRow(0, laLatitude, tfLatitude, laLongitude, tfLongitude, laElevation, tfElevation);
        paramsGrid.addRow(1, laAzimuth, tfAzimuth, laHorizontalFieldOfView, tfHorizontalFieldOfView, laMaxDistance, tfMaxDistance);
        paramsGrid.addRow(2, laWidth, tfWidth, laHeight, tfHeight, laSuperSamplingExponent, cbSuperSamplingExponent);



        paramsGrid.add(infos, 6, 0,1, 3);

        return paramsGrid;
    }

    private TextField buildTextField(ObjectProperty<Integer> property, int prefColumnCount, int decimal){
        StringConverter<Integer> stringConverter = new FixedPointStringConverter(decimal);
        TextFormatter<Integer> formatter = new TextFormatter<>(stringConverter);

        TextField textField = new TextField();
        textField.setAlignment(Pos.BASELINE_RIGHT);
        textField.setPrefColumnCount(prefColumnCount);
        formatter.valueProperty().bindBidirectional(property);
        textField.setTextFormatter(formatter);

        return textField;
    }
}