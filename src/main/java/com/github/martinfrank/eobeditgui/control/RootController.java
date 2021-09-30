package com.github.martinfrank.eobeditgui.control;

import com.github.martinfrank.eobedit.data.SavegameFile;
import com.github.martinfrank.eobedit.event.ChangeEventType;
import com.github.martinfrank.eobedit.event.PlayerDataChangeEventListener;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class RootController implements PlayerDataChangeEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootController.class);

    private final SavegameFile model;
    private final FileChooser fileChooser = new FileChooser();

    private InfoController infoController;
    private PageAController pageAController;
    private PageBController pageBController;
    private Stage stage;

    RootController(SavegameFile model) {
        this.model = model;
        model.registerChangeListener(this);
    }

    public void load(ActionEvent actionEvent) throws IOException {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            model.load(file);
        }
    }

    public void save(ActionEvent actionEvent) throws IOException {
        if(model.hasUnsavedChanges()){
            model.save();
        }
    }

    public void exit(ActionEvent actionEvent) {
        stage.fireEvent(new WindowEvent(null, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void about(ActionEvent actionEvent) {
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @Override
    public void playerDataChanged(int playerDataIndex, ChangeEventType type) {
        LOGGER.debug("changes on player:{}, changed data:{}",playerDataIndex, type);
        switch (type){
            case LOAD_DATA: {
                pageAController.updateModel(model, 0);
                pageBController.updateModel(model, 0);
            }
        }
    }

    public void init() {
        pageAController.init();
        pageBController.init();
    }

    public void setInfoController(InfoController infoController) {
        this.infoController = infoController;
    }

    public void setPageAController(PageAController pageAController) {
        this.pageAController = pageAController;
    }

    public void setPageBController(PageBController pageBController) {
        this.pageBController = pageBController;
    }

    public void nextPlayer() {
        LOGGER.debug("nextPlayer");
    }

    public void prevPlayer() {
        LOGGER.debug("prevPlayer");
    }
}
