package com.github.martinfrank.eobeditgui.control;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.data.Portrait;
import com.github.martinfrank.eobedit.data.SavegameFile;
import com.github.martinfrank.eobedit.event.ChangeEventType;
import com.github.martinfrank.eobedit.event.PlayerDataChangeEventListener;
import com.github.martinfrank.eobeditgui.model.SavegameFileModel;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class RootController implements PlayerDataChangeEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootController.class);

    private final SavegameFileModel model;
    private final FileChooser fileChooser = new FileChooser();

    private InfoController infoController;
    private PageAController pageAController;
    private PageBController pageBController;
    private PreselectController preselectController;
    private Stage stage;

    RootController(SavegameFileModel model) {
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

//                pageAController.updateModel(model, 0);
//                pageBController.updateModel(model, 0);
                LOGGER.debug("here would come an update!!");
            }
        }
    }

    public void init() {
        pageAController.init(model);
        pageBController.init(model);
        preselectController.init(model);
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

    public void setPreselectController(PreselectController preselectController) {
        this.preselectController = preselectController;
    }

    public void nextPlayer() {
        LOGGER.debug("nextPlayer");
    }

    public void prevPlayer() {
        LOGGER.debug("prevPlayer");
    }

    public void setPortrait(Portrait portrait) {
        model.setPortrait(portrait);
    }

    public void updateItemInfo(Item item) {
        infoController.updateItemInfo(item);
    }

    public void setInventoryItem(Item item, int index) {
        model.setInventoryItem(item,index);
        LOGGER.debug("setting item slot {} with item: {}",index,item.description);
    }

    public void updateItemInventoy(int index) {
        infoController.updateItemInfo(Items.ADAMATITE_DART_J);
    }

    public Property<Image> getInventoryImageProperty(int index) {
        return model.inventoryImageProperty(index);
    }
}
