package com.github.martinfrank.eobeditgui.model;

import com.github.martinfrank.eobedit.data.Portrait;
import javafx.scene.image.Image;

public class PortraitModel {

    public final Image image;
    public final Portrait portrait;

    public PortraitModel(Image image, Portrait portrait){
        this.image = image;
        this.portrait = portrait;
    }

}
