package com.github.martinfrank.eobeditgui.model;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Portrait;
import com.github.martinfrank.eobedit.data.SavegameFile;
import com.github.martinfrank.eobedit.event.PlayerDataChangeEventListener;
import com.github.martinfrank.eobedit.image.ImageProvider;
import com.github.martinfrank.eobeditgui.control.PageAController;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SavegameFileModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavegameFileModel.class);

    private final SavegameFile savegameFile = new SavegameFile();
    private final ImageProvider imageProvider = new ImageProvider();

    private StringProperty nameProperty;
    private final String NAME_PROPERTY = "name";

    private Property<Image> portraitProperty;// = new SimpleObjectProperty<>();
    private final String PORTRAIT_PROPERTY = "portrait";

    private List<Property<Image>> inventoryItems = new ArrayList<>();

    private int currentPlayerIndex = 0;

    public SavegameFileModel() {
        bindProperties();
    }

    private void bindProperties() {
        nameProperty = new SimpleStringProperty(this, NAME_PROPERTY, "?");
        nameProperty.addListener((observableValue, oldValue, newValue) -> {
            LOGGER.debug("change on name property: {} --> {}", oldValue, newValue);
            if(savegameFile.getPlayer(currentPlayerIndex) != null){
                savegameFile.getPlayer(currentPlayerIndex).setName(newValue);
            }
        });
        portraitProperty = new SimpleObjectProperty<>(this, PORTRAIT_PROPERTY, null);
        portraitProperty.addListener((observableValue, oldValue, newValue) -> LOGGER.debug("change on portrait property: {} --> {}", oldValue, newValue));

        for(int i = 0; i < PageAController.AMOUNT_SLOTS; i ++){
            Property<Image> inventoryItemProperty  = new SimpleObjectProperty<>();
            inventoryItems.add(inventoryItemProperty);
        }
    }

    //---name---
    public void setName(String name) {
        nameProperty.set(name);
        LOGGER.debug("setting name: {}", name);
        savegameFile.getPlayer(currentPlayerIndex).setName(name);
    }

    public String getName() {
        LOGGER.debug("getting name");
        return savegameFile.getPlayer(currentPlayerIndex).getName();
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    //---portrait---
    public Property<Image> portraitProperty() {
        return portraitProperty;
    }

    public void setPortrait(Image image) {
        LOGGER.debug("setting portrait: {}", image);
    }

    public Image getPortrait() {
        LOGGER.debug("getting name");
        return getCurrentPortrait();
    }

    public void registerChangeListener(PlayerDataChangeEventListener listener) {
        savegameFile.registerChangeListener(listener);
    }

    public void load(File file) throws IOException {
        savegameFile.load(file);
        nameProperty.set(savegameFile.getPlayer(currentPlayerIndex).getName());
        portraitProperty.setValue(getCurrentPortrait());
        savegameFile.resetUnsavedChanges();
        for(int i = 0; i < PageAController.AMOUNT_SLOTS; i ++){
            inventoryImageProperty(i).setValue(getItemImage(savegameFile.getPlayer(currentPlayerIndex).getInventory(i)));
        }
    }

    public boolean hasUnsavedChanges() {
        return savegameFile.hasUnsavedChanges();
    }

    public void save() throws IOException {
        savegameFile.save();
    }

    private Image getCurrentPortrait() {
        return SwingFXUtils.toFXImage(imageProvider.getPortrait(savegameFile.getPlayer(currentPlayerIndex).getPortrait()), null);
    }

    private Image getItemImage(Item item) {
        return SwingFXUtils.toFXImage(imageProvider.getItem(item), null);
    }

    public void setPortrait(Portrait portrait) {
        if(savegameFile.getPlayer(currentPlayerIndex) != null){
            savegameFile.getPlayer(currentPlayerIndex).setPortrait(portrait);
            portraitProperty.setValue(getCurrentPortrait());
        }
    }

    public void setInventoryItem(Item item, int index) {
        if(savegameFile.getPlayer(currentPlayerIndex) != null && item != null){
            savegameFile.getPlayer(currentPlayerIndex).setInventory(index,item);
            inventoryItems.get(index).setValue(getItemImage(item));
        }
    }

    public Property<Image> inventoryImageProperty(int index) {
        return inventoryItems.get(index);
    }
}
