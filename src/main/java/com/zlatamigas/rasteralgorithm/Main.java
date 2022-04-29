package com.zlatamigas.rasteralgorithm;

import com.zlatamigas.rasteralgorithm.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private static String APP_NAME = "Raster algorithm";
    private static String APP_PAGE = "sample.fxml";
    private static String STYLESHEET = "application.css";
    private static String ICON = "icon.png";

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(Main.class.getResource(APP_PAGE));
        Parent root = (Parent) loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());

        Controller controller = (Controller) loader.getController();
        controller.setStage(primaryStage);

        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream(ICON)));
        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
