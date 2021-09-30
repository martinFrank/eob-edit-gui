package com.github.martinfrank.eobeditgui.control;

import com.github.martinfrank.eobedit.data.SavegameFile;
import com.github.martinfrank.eobedit.image.ImageProvider;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageBController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageBController.class);

    private final ImageProvider imageProvider = new ImageProvider();
    private final RootController rootController;

    @FXML
    public ImageView portraitB;

    @FXML
    public TextField nameTextB;

    @FXML
    public AnchorPane panePageB;

    public PageBController(RootController rootController) {
        this.rootController = rootController;
    }

    public void init() {
        nameTextB.setFont(FontProvider.getDefaultFont());
        nameTextB.setPadding(new Insets(0,0,0,0));
    }

    public void updateModel(SavegameFile model, int index) {
        portraitB.setImage(SwingFXUtils.toFXImage(imageProvider.getPortrait(model.getPlayer(0).getPortrait()), null));
        nameTextB.setText(model.getPlayer(0).getName() );
        panePageB.setBackground(new Background(
                new BackgroundImage(SwingFXUtils.toFXImage(imageProvider.getGuiPageB(), null),
                        null,
                        null,
                        null,
                        null)));
    }

    public void nextPlayer(ActionEvent actionEvent) {
        rootController.nextPlayer();
    }

    public void prevPlayer(ActionEvent actionEvent) {
        rootController.prevPlayer();
    }
}
