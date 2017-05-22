package ch.epfl.alpano.gui;

import static ch.epfl.alpano.summit.GazetteerParser.readSummitsFrom;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.DiscreteElevationModel;
import ch.epfl.alpano.dem.HgtDiscreteElevationModel;
import javafx.scene.Node;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class TestLabelizer extends Application {
    final static File HGT_FILE1 = new File("N46E007.hgt");
    final static File HGT_FILE2 = new File("N46E006.hgt");
    final static File SUMMIT = new File("alps.txt");

    public static void main(String[] args) throws IOException{
        launch(args);
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //try (DiscreteElevationModel dDEM =
         //       new HgtDiscreteElevationModel(HGT_FILE1)) {
            DiscreteElevationModel dDEM =
                    new HgtDiscreteElevationModel(HGT_FILE1);
            ContinuousElevationModel cDEM = new ContinuousElevationModel(dDEM);
            
            Labelizer l = new Labelizer(cDEM,readSummitsFrom(SUMMIT));
        
            List<Node> list = l.labels(PredefinedPanoramas.NIESEN.panoramaParameters());
            
            for(Node n : list)
                System.out.println(n);
        //}
        
        Platform.exit();
    }
}
