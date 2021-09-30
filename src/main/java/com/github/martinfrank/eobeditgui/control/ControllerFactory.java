package com.github.martinfrank.eobeditgui.control;

import com.github.martinfrank.eobedit.data.SavegameFile;
import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {

    private RootController rootController;
    private final SavegameFile model;

//    public ControllerFactory() {
//    }

    public ControllerFactory(SavegameFile model) {
        this.model = model;
    }

    @Override
    public Object call(Class<?> type) {
        if (type == RootController.class) {
            rootController = new RootController(model);
            return rootController;
        }
        if (type == InfoController.class) {
            InfoController infoController = new InfoController();
            rootController.setInfoController(infoController);
            return infoController;
        }
        if (type == PageAController.class) {
            PageAController pageAController = new PageAController(rootController);
            rootController.setPageAController(pageAController);
            return pageAController;
        }
        if (type == PageBController.class) {
            PageBController pageBController = new PageBController(rootController);
            rootController.setPageBController(pageBController);
            return pageBController;
        }else {
            // default behavior for controllerFactory:
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception exc) {
                exc.printStackTrace();
                throw new RuntimeException(exc); // fatal, just bail...
            }
        }
    }

    public RootController getRootController() {
        return rootController;
    }

}
