package com.github.martinfrank.eobeditgui.control;

import com.github.martinfrank.eobedit.data.ByteArrayTool;
import com.github.martinfrank.eobedit.data.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Arrays;

public class InfoController {

    @FXML
    public Label playerInfoLabel;

    public void setPlayerInfo(String info) {
        playerInfoLabel.setText(info);
    }

    public void updateItemInfo(Item item) {
        if (item != null) {
            String h = Integer.toHexString(0xFF&item.id[0]);
            String l = Integer.toHexString(0xFF&item.id[1]);
            playerInfoLabel.setText("Item ["+h+","+l+"]: " + item.description + ", long desc: " + item.details);
        }
    }
}
