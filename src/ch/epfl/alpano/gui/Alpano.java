package ch.epfl.alpano.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public final class Alpano extends Application {
  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    // … création de l'interface graphique

    BorderPane root = …;
    Scene scene = new Scene(root);

    primaryStage.setTitle("Alpano");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

@Override
public void start(Stage arg0) throws Exception {
    // TODO Auto-generated method stub
    
}
}