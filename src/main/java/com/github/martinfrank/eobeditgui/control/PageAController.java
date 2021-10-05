package com.github.martinfrank.eobeditgui.control;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.data.SavegameFile;
import com.github.martinfrank.eobedit.image.ImageProvider;
import com.github.martinfrank.eobeditgui.model.SavegameFileModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class PageAController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageAController.class);
    public static final int AMOUNT_SLOTS = 14; //FIXME is this the right place?
    private static final double BACKGROUND_WIDTH = 36;
    private static final double BACKGROUND_HEIGHT = 36;

    private final ImageProvider imageProvider = new ImageProvider();
    private final RootController rootController;


    @FXML
    public ImageView portraitA;

    @FXML
    public TextField nameTextA;

    @FXML
    public AnchorPane panePageA;

    @FXML
    public GridPane itemGrid;

    public PageAController(RootController rootController) {
        this.rootController = rootController;
    }

    public void init(SavegameFileModel model) {
        nameTextA.setFont(FontProvider.getDefaultFont());
        nameTextA.setPadding(new Insets(0, 0, 0, 0));
        nameTextA.textProperty().bindBidirectional(model.nameProperty());
        portraitA.imageProperty().bind(model.portraitProperty());

        panePageA.setBackground(new Background(
                new BackgroundImage(SwingFXUtils.toFXImage(imageProvider.getGuiPageA(), null),
                        null,
                        null,
                        null,
                        null)));

        for (int index = 0; index < AMOUNT_SLOTS; index++) {
            int dx = index % 2;
            int dy = index / 2;
//            itemGrid.add(createItemNode(Items.ITEMS[index+14], index),dx,dy);
            itemGrid.add(createItemNode(index), dx, dy);
        }
        itemGrid.setPrefWidth(2 * BACKGROUND_WIDTH);
        itemGrid.setPrefHeight(7 * BACKGROUND_HEIGHT);
    }

    //    private Pane createItemNode(Item item, int index) {
    private Pane createItemNode(int index) {
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
        imageView.imageProperty().bind(rootController.getInventoryImageProperty(index));

        AnchorPane.setLeftAnchor(imageView, 2d);
        AnchorPane.setTopAnchor(imageView, 2d);

        pane.setOnDragOver(dragEvent -> {
            if (dragEvent.getGestureSource() != pane &&
                    dragEvent.getDragboard().hasString() &&
                    dragEvent.getDragboard().getString().startsWith(ItemDataTransfer.PRESELECT_SOURCE)) {
                dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            dragEvent.consume();
        });

        pane.setOnDragDropped(dragEvent -> {
            Dragboard dragboard = dragEvent.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                String itemId = dragEvent.getDragboard().getString().replace(ItemDataTransfer.PRESELECT_SOURCE, "");
                //FIXME - this method belongs into Items!
                Item it = Arrays.stream(Items.ITEMS).filter(i -> i.getId().equals(itemId)).findAny().orElse(Items.NOTHING_A);
                LOGGER.debug("onDragDropped succeeded!!! {}", it);
                rootController.setInventoryItem(it, index);
                success = true;
            }
            dragEvent.setDropCompleted(success);
            dragEvent.consume();
        });

        pane.setOnMouseClicked(mouseEvent -> rootController.updateItemInventoy(index));

        pane.getChildren().add(imageView);
        return pane;
    }

    public void updateModel(SavegameFile model, int index) {
        portraitA.setImage(SwingFXUtils.toFXImage(imageProvider.getPortrait(model.getPlayer(0).getPortrait()), null));
        nameTextA.setText(model.getPlayer(0).getName());
    }

    public void nextPlayer(ActionEvent actionEvent) {
        rootController.nextPlayer();
    }

    public void prevPlayer(ActionEvent actionEvent) {
        rootController.prevPlayer();
    }
}
