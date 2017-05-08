package ch.epfl.alpano.gui;

import static ch.epfl.alpano.summit.GazetteerParser.readSummitsFrom;

import java.io.File;
import java.util.List;

import ch.epfl.alpano.Interval2D;
import ch.epfl.alpano.dem.DiscreteElevationModel;
import ch.epfl.alpano.dem.HgtDiscreteElevationModel;
import ch.epfl.alpano.summit.Summit;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try (DiscreteElevationModel dDEM = (new HgtDiscreteElevationModel(HGT_FILE1))
                .union(new HgtDiscreteElevationModel(HGT_FILE2)
                .union(new HgtDiscreteElevationModel(HGT_FILE3)
                .union(new HgtDiscreteElevationModel(HGT_FILE4)
                .union(new HgtDiscreteElevationModel(HGT_FILE5)
                .union(new HgtDiscreteElevationModel(HGT_FILE6)
                .union(new HgtDiscreteElevationModel(HGT_FILE7)
                .union(new HgtDiscreteElevationModel(HGT_FILE8))))))))) {
            List<Summit> summits = readSummitsFrom(SUMMIT_FILE);
            // … création de l'interface graphique
            
            

           BorderPane root = ;
           Scene scene = new Scene(root);

           primaryStage.setTitle("Alpano");
           primaryStage.setScene(scene);
           primaryStage.show();
            
        }
    }

}