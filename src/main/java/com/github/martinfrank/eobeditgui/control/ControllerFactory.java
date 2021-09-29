package com.github.martinfrank.eobeditgui.control;

import com.github.martinfrank.eobedit.data.SavegameFile;
import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {

    private Controller rootController;
    private final SavegameFile model;

//    public ControllerFactory() {
//    }

    public ControllerFactory(SavegameFile model) {
        this.model = model;
    }

    @Override
    public Object call(Class<?> type) {
        if (type == Controller.class) {
            rootController = new Controller(model);
            return rootController;
        } else {
            // default behavior for controllerFactory:
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception exc) {
                exc.printStackTrace();
                throw new RuntimeException(exc); // fatal, just bail...
            }
        }
    }

    public Controller getRootController() {
        return rootController;
    }


}
