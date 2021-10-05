package com.github.martinfrank.eobeditgui;

import com.github.martinfrank.eobedit.data.SavegameFile;
import com.github.martinfrank.eobeditgui.control.RootController;
import com.github.martinfrank.eobeditgui.control.ControllerFactory;
import com.github.martinfrank.eobeditgui.model.SavegameFileModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class App extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private Pane pane;
    private RootController presenter;
    private SavegameFileModel model;

    public static void main(String[] args) {
        launch(args);
    }

    // Snippet: https://www.tcs.ifi.lmu.de/lehre/ws-2017-18/sep/material/mvc.pdf
    // Model model = newModel();
    // View view = newView(model, stage);
    // RootController controller = newController(model, view);

    //model-view-presenter
    //siehe auch https://support.touchgfx.com/docs/development/ui-development/software-architecture/model-view-presenter-design-pattern
    //Model model = new Model();
    //Presenter p = new Presenter(model) //hätte dedizierte SSt für den View; hätte Listener für Model updates(hier nicht nötig)
    //view view = new View(p);
    //p.setView(view);

    @Override
    public void init() {
        model = new SavegameFileModel();
        ControllerFactory controllerFactory = new ControllerFactory(model);
        try {
            File f = new File("src/main/resources/gui/root.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(f.toURI().toURL());
            fxmlLoader.setControllerFactory(controllerFactory);
            pane = fxmlLoader.load();
        } catch (IOException e) {
            LOGGER.debug("error", e);
        }
        presenter = controllerFactory.getRootController();
    }

    @Override
    public void start(Stage stage) {
        if (pane != null) {
            stage.setScene(new Scene(pane));
            presenter.setStage(stage);
            presenter.init();
            stage.setTitle("eob 1 editor");

            stage.setOnCloseRequest(windowEvent -> {
                if(model.hasUnsavedChanges()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Quit application");
                    alert.setContentText("Close without saving?");
                    alert.initOwner(stage.getOwner());
                    Optional<ButtonType> res = alert.showAndWait();
                    if (!ButtonType.OK.equals(res.orElse(null))) {
                        windowEvent.consume();
                    }
                }
            });

            stage.show();

        } else {
            LOGGER.debug("error during init");
            Platform.exit();
            System.exit(0);
        }
    }

}
