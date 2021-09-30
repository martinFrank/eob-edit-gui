package com.github.martinfrank.eobeditgui.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InfoController {

    @FXML
    public Label playerInfoLabel;

    public void setPlayerInfo(String info) {
        playerInfoLabel.setText(info);
    }
}
