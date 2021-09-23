package com.github.martinfrank.eobeditgui;

import com.github.martinfrank.eobeditgui.control.Controller;
import com.github.martinfrank.eobeditgui.control.ControllerFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class App extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private Pane root;

    private Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        ControllerFactory controllerFactory = new ControllerFactory();
        try {
            File f = new File("src/main/resources/gui/root.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(f.toURI().toURL());
            fxmlLoader.setControllerFactory(controllerFactory);
            root = fxmlLoader.load();
        } catch (IOException e) {
            LOGGER.debug("error", e);
        }
        controller = controllerFactory.getRootController();
        controller.init();
     }

    @Override
    public void start(Stage stage) {
        if (root != null) {
            stage.setScene(new Scene(root));
            stage.setTitle("tbd: set title");
            stage.show();
        } else {
            LOGGER.debug("error during init");
            Platform.exit();
            System.exit(0);
        }
    }

}
