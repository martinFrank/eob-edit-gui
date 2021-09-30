package com.github.martinfrank.eobeditgui.control;

import com.github.martinfrank.eobedit.data.SavegameFile;
import com.github.martinfrank.eobedit.image.ImageProvider;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageAController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageAController.class);

    private final ImageProvider imageProvider = new ImageProvider();

    @FXML
    public ImageView portraitA;

    @FXML
    public TextField nameTextA;

    @FXML
    public AnchorPane panePageA;

    public void init() {
        nameTextA.setFont(FontProvider.getDefaultFont());
        nameTextA.setPadding(new Insets(0,0,0,0));
    }

    public void updateModel(SavegameFile model) {
        portraitA.setImage(SwingFXUtils.toFXImage(imageProvider.getPortrait(model.getPlayer(0).getPortrait()), null));
        nameTextA.setText(model.getPlayer(0).getName() );
        panePageA.setBackground(new Background(
                new BackgroundImage(SwingFXUtils.toFXImage(imageProvider.getGuiPageA(), null),
                        null,
                        null,
                        null,
                        null)));
    }
}
