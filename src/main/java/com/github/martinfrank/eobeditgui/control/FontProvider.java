package com.github.martinfrank.eobeditgui.control;

import javafx.scene.text.Font;

public class FontProvider {

    private static final float DEFAULT_FONT_SIZE = 14f;

    public static Font getFont(float size){
        return Font.loadFont(FontProvider.class.getResourceAsStream("retro_computer_personal_use.ttf"), size);
    }

    public static Font getDefaultFont(){
        return getFont(DEFAULT_FONT_SIZE);
    }
}
