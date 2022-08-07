package com.legyver.fenxlib.samples.alerts;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.api.alert.AlertTextContent;
import com.legyver.fenxlib.api.alert.IAlert;
import com.legyver.fenxlib.api.alert.Level;
import com.legyver.fenxlib.api.config.options.ApplicationOptions;
import com.legyver.fenxlib.api.context.ApplicationContext;
import com.legyver.fenxlib.api.uimodel.IUiModel;
import com.legyver.fenxlib.config.json.JsonApplicationConfig;
import com.legyver.fenxlib.core.controls.factory.SceneFactory;
import com.legyver.fenxlib.core.layout.BorderPaneApplicationLayout;
import com.legyver.fenxlib.core.layout.options.CenterRegionOptions;
import com.legyver.fenxlib.core.menu.templates.MenuBuilder;
import com.legyver.fenxlib.core.menu.templates.section.FileExitMenuSection;
import com.legyver.fenxlib.samples.alerts.form.AlertGeneratingForm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;

public class MyApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationOptions applicationOptions = new ApplicationOptions.Builder<>()
                .appName("FenxlibAlertsDemo")
                .customAppConfigInstantiator(JsonApplicationConfig::new)
                .uiModel(new ApplicationUIModel())
                .styleSheetUrl(MyApplication.class.getResource("application.css"))
                .displayAlerts(Level.ERROR, IAlert.TargetRegion.APPLICATION_BOTTOM_RIGHT)
                .displayAlerts(Level.WARNING, IAlert.TargetRegion.APPLICATION_BOTTOM_RIGHT)
                .displayAlerts(Level.INFO, IAlert.TargetRegion.APPLICATION_TOP_RIGHT)
                .resourceBundle("com.legyver.fenxlib.samples.alerts.demo")
                .build();
        applicationOptions.startup(this, primaryStage);

        //you would customize alert size (per window position) with below.
        //Below only customizes the bottom right anchor.
//        IAlert.TargetRegion.APPLICATION_BOTTOM_RIGHT.setAlertAnchor(new BottomRightAnchor(30, 30));

        SceneFactory sceneFactory = new SceneFactory(primaryStage);

        BorderPaneApplicationLayout borderPaneApplicationLayout = new BorderPaneApplicationLayout.BorderPaneBuilder()
                .title("fenxlib.demo.title")//Fenxlib Alerts Demo
                .width(600.0)
                .height(800.0)
                .centerRegionOptions(new CenterRegionOptions(alertGeneratingForm()))
                .menuBar(menuBar())
                .build();

        Scene scene = sceneFactory.makeScene(borderPaneApplicationLayout);
        primaryStage.show();

//        ScenicView.show(scene);
    }

    private AlertGeneratingForm alertGeneratingForm() {
        AlertGeneratingForm alertGeneratingForm = new AlertGeneratingForm();
        alertGeneratingForm.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (NumberUtils.isDigits(newValue)) {
                ApplicationContext.infoAlert(new AlertTextContent("fenxlib.demo.ok.message", newValue), 1000L);
            } else if (NumberUtils.isParsable(newValue)) {
                ApplicationContext.warningAlert(new AlertTextContent("fenxlib.demo.warning.message", newValue), 2000L);
            } else {
                ApplicationContext.errorAlert(new AlertTextContent("fenxlib.demo.error.message", newValue));
            }
        });
        return alertGeneratingForm;
    }

    private MenuBar menuBar() throws CoreException {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new MenuBuilder()
                .name("File")
                .menuSection(new FileExitMenuSection())
                .build();
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    private static class ApplicationUIModel implements IUiModel {

    }
}
