package com.github.martinfrank.eobeditgui.control;

import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FontProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageAController.class);
    private static final double DEFAULT_FONT_SIZE = 14d;

    public static Font getFont(double size) {
        return Font.loadFont(Thread.currentThread().getContextClassLoader().
                getResourceAsStream("retro_computer_personal_use.ttf"), size);
    }

    public static Font getDefaultFont() {
        return getFont(DEFAULT_FONT_SIZE);
    }
}
