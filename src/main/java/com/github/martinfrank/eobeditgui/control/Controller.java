package com.github.martinfrank.eobeditgui.control;

import com.github.martinfrank.eobedit.data.SavegameFile;
import com.github.martinfrank.eobedit.event.ChangeEventType;
import com.github.martinfrank.eobedit.event.PlayerDataChangeEventListener;
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
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class Controller implements PlayerDataChangeEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    private final SavegameFile model;
    private final ImageProvider imageProvider = new ImageProvider();
    private final FileChooser fileChooser = new FileChooser();

    @FXML
    public AnchorPane panePageB;

    @FXML
    public AnchorPane panePageA;

    @FXML
    public TextField nameTextA;

    @FXML
    public TextField nameTextB;

    @FXML
    public ImageView portraitA;

    @FXML
    public ImageView portraitB;


    private Stage stage;


    Controller(SavegameFile model) {
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
                portraitA.setImage(SwingFXUtils.toFXImage(imageProvider.getPortrait(model.getPlayer(0).getPortrait()), null));
                portraitB.setImage(SwingFXUtils.toFXImage(imageProvider.getPortrait(model.getPlayer(0).getPortrait()), null));
                nameTextA.setText(model.getPlayer(0).getName() );
                nameTextB.setText(model.getPlayer(0).getName() );
                panePageB.setBackground(new Background(
                        new BackgroundImage(SwingFXUtils.toFXImage(imageProvider.getGuiPageB(), null),
                                null,
                                null,
                                null,
                                null)));
                panePageA.setBackground(new Background(
                        new BackgroundImage(SwingFXUtils.toFXImage(imageProvider.getGuiPageA(), null),
                                null,
                                null,
                                null,
                                null)));
            }
        }
    }

    public void init() {
        nameTextA.setFont(getFont(14));
        nameTextA.setPadding(new Insets(0,0,0,0));
        nameTextB.setFont(getFont(14));
        nameTextB.setPadding(new Insets(0,0,0,0));
    }

    private Font getFont(float size){
        return Font.loadFont(getClass().getResourceAsStream("retro_computer_personal_use.ttf"), size);
    }
}
