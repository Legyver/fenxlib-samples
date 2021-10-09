package com.legyver.fenxlib.samples.alerts;

import com.legyver.fenxlib.core.api.locator.query.ComponentQuery;
import com.legyver.fenxlib.core.api.uimodel.IUiModel;
import com.legyver.fenxlib.core.impl.config.JsonApplicationConfig;
import com.legyver.fenxlib.core.impl.config.options.ApplicationOptions;
import com.legyver.fenxlib.core.impl.context.ApplicationContext;
import com.legyver.fenxlib.core.impl.factory.*;
import com.legyver.fenxlib.core.impl.factory.menu.*;
import com.legyver.fenxlib.core.impl.factory.options.BorderPaneInitializationOptions;
import com.legyver.fenxlib.core.impl.factory.options.RegionInitializationOptions;
import com.legyver.fenxlib.widgets.about.AboutMenuItemFactory;
import com.legyver.fenxlib.widgets.about.AboutPageOptions;
import com.legyver.fenxlib.samples.alerts.form.AlertGeneratingForm;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;
import org.scenicview.ScenicView;

import java.util.Optional;
import java.util.function.Supplier;

public class MyApplication  extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        new ApplicationOptions.AutoStartBuilder<>()
                .appName("FenxlibAlertsDemo")
                .customAppConfigInstantiator(JsonApplicationConfig::new)
                .uiModel(new ApplicationUIModel())
                .build();

        AboutPageOptions aboutPageOptions = new AboutPageOptions.Builder(getClass())
//                .buildPropertiesFile("build.properties")
//                .copyrightPropertiesFile("copyright.properties")
                .title("MyApplication")
                .intro("MyApplication makes amazing things easy")
                .gist("More stuff about how great this app is.  I can go on about it for a really long time and the text will wrap around.")
                .additionalInfo("be sure to tell your friends")
                .build();

        SceneFactory sceneFactory = new SceneFactory(primaryStage, 600, 675, MyApplication.class.getResource("application.css"));

       Supplier<StackPane> centerContentReference = () -> {
            Optional<StackPane> center = new ComponentQuery.QueryBuilder()
                    .inRegion(BorderPaneInitializationOptions.REGION_CENTER)
                    .type(StackPane.class).execute();
            return center.get();
        };

        AlertGeneratingForm alertGeneratingForm = new AlertGeneratingForm();
        alertGeneratingForm.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (NumberUtils.isDigits(newValue)) {
                ApplicationContext.infoAlert(centerContentReference.get(), "OK", "Number entered: " + newValue, 1000L);
            } else if (NumberUtils.isParsable(newValue)) {
                ApplicationContext.warningAlert(centerContentReference.get(), "Warning", "Number contains a decimal: " + newValue, 2000L);
            } else {
                ApplicationContext.errorAlert(centerContentReference.get(), "Bad value", "Value is not a number: " + newValue);
            }
        });

        BorderPaneInitializationOptions options = new BorderPaneInitializationOptions.Builder()
                .center(new RegionInitializationOptions.Builder()
                        //this StackPane will be queried by the above centerContentReference
                        .factory(new StackPaneRegionFactory(true, (x) -> alertGeneratingForm))
                )
                .top(new RegionInitializationOptions.Builder()
                        .factory(new TopRegionFactory(
                                new LeftMenuOptions(
                                        new MenuFactory("File",
                                                new ExitMenuItemFactory("Exit")
                                        )
                                ),
                                new CenterOptions(new TextFieldFactory(false)),
                                new RightMenuOptions(
                                        new MenuFactory("Help", new AboutMenuItemFactory("About", centerContentReference, aboutPageOptions))
                                )
                        )))
               .build();

        BorderPane root = new BorderPaneFactory(options).makeBorderPane();
        Scene scene = sceneFactory.makeScene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Alerts Demo");
        primaryStage.show();

//        ScenicView.show(scene);

    }

    private static class ApplicationUIModel implements IUiModel {

    }
}
