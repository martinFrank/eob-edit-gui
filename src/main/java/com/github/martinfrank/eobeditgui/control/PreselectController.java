package com.github.martinfrank.eobeditgui.control;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.data.Portrait;
import com.github.martinfrank.eobedit.image.ImageProvider;
import com.github.martinfrank.eobeditgui.model.SavegameFileModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class PreselectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreselectController.class);

    private final RootController rootController;
    private static final int GRID_SIZE = 5;
    private static final double BACKGROUND_WIDTH = 36;
    private static final double BACKGROUND_HEIGHT = 36;
    private static final double ITEM_WIDTH = 32;
    private static final double ITEM_HEIGHT = 32;

    @FXML
    public ComboBox<Portrait> portraitSelect;

    @FXML
    public GridPane itemGrid;

    private final Map<Portrait, Image> portraitBuffer = new EnumMap<>(Portrait.class);
    private final ImageProvider imageProvider = new ImageProvider();


    public PreselectController(RootController rootController) {
        this.rootController = rootController;
    }

    public void init(SavegameFileModel model) {
        Arrays.stream(Portrait.values()).forEach(p -> portraitBuffer.put(p, SwingFXUtils.toFXImage(imageProvider.getPortrait(p), null)));
        ObservableList<Portrait> obList = FXCollections.observableArrayList(Portrait.values());
        portraitSelect.setItems(obList);
        portraitSelect.setButtonCell(getListCell());
        portraitSelect.setCellFactory(c -> getListCell());
        portraitSelect.setVisibleRowCount(4);
        portraitSelect.getSelectionModel().select(0);

        for (int index = 0; index < Items.ITEMS.length; index++){
            int dx = index % GRID_SIZE;
            int dy = index / GRID_SIZE;
            itemGrid.add(createItemNode(Items.ITEMS[index]),dx,dy);
        }
        itemGrid.setPrefWidth(GRID_SIZE * BACKGROUND_WIDTH);
        int h = Items.ITEMS.length/GRID_SIZE;
        itemGrid.setPrefHeight(h * BACKGROUND_HEIGHT);
    }

    private Pane createItemNode(Item item) {
        AnchorPane pane = new AnchorPane();
        pane.setBackground(new Background(
                new BackgroundImage(SwingFXUtils.toFXImage(imageProvider.getItemBackground(), null),
                        null,
                        null,
                        null,
                        null)));
        pane.setPrefWidth(BACKGROUND_WIDTH);
        pane.setPrefHeight(BACKGROUND_HEIGHT);
        pane.setMinWidth(BACKGROUND_WIDTH);
        pane.setMinHeight(BACKGROUND_HEIGHT);

        ImageView imageView = new ImageView();
        imageView.setImage(SwingFXUtils.toFXImage(imageProvider.getItem(item), null));
        AnchorPane.setLeftAnchor(imageView, 2d);
        AnchorPane.setTopAnchor(imageView, 2d);

        pane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                LOGGER.debug("onDrag detected...");
                Dragboard dragboard = pane.startDragAndDrop(TransferMode.ANY);
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(ItemDataTransfer.PRESELECT_SOURCE +item.getId());
                clipboardContent.putImage(imageView.getImage());
                dragboard.setContent(clipboardContent);
                mouseEvent.consume();
            }

        });

        pane.setOnDragDone(Event::consume);
        pane.setOnMouseClicked(mouseEvent -> rootController.updateItemInfo(item));

        pane.getChildren().add(imageView);
        return pane;
    }

    @SuppressWarnings("squid:S1172") // auto-generated method
    public void setPortrait(ActionEvent actionEvent) {
        Portrait portrait = portraitSelect.getValue();
        LOGGER.debug("selected to set: {}", portrait);
        rootController.setPortrait(portrait);

    }

    @SuppressWarnings("squid:S110")
    private ListCell<Portrait> getListCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Portrait portrait, boolean b) {
                super.updateItem(portrait, b);
                setGraphic(portrait == null ? null : new ImageView(portraitBuffer.get(portrait)));
            }
        };
    }


}
