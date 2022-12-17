package com.legyver.fenxlib.samples.hello;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.api.config.options.ApplicationOptions;
import com.legyver.fenxlib.api.controls.ControlsFactory;
import com.legyver.fenxlib.api.i18n.ResourceBundleServiceRegistry;
import com.legyver.fenxlib.api.locator.LocationContext;
import com.legyver.fenxlib.api.scene.controls.options.TextFieldOptions;
import com.legyver.fenxlib.api.scene.layout.options.VBoxOptions;
import com.legyver.fenxlib.core.controls.factory.SceneFactory;
import com.legyver.fenxlib.core.layout.SinglePaneApplicationLayout;
import com.legyver.fenxlib.core.menu.templates.MenuBuilder;
import com.legyver.fenxlib.core.menu.templates.section.FileExitMenuSection;
import com.legyver.fenxlib.core.util.LocationContextOperator;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class MyApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties buildProperties = new Properties();
        buildProperties.load(MyApplication.class.getResourceAsStream("/build.properties"));
        ApplicationOptions applicationOptions = new ApplicationOptions.Builder<>()
                .appName("Hello World")
                .buildProperties(buildProperties)
                .resourceBundle("com.legyver.fenxlib.samples.hello.labels")
                .build();
        applicationOptions.startup(this, primaryStage);

        SinglePaneApplicationLayout borderPaneApplicationLayout = new SinglePaneApplicationLayout.SinglePaneApplicationLayoutBuilder()
                .title("fenxlib.demo.title")
                .width(600.0)
                .height(800.0)
                .menuBar(menuBar())
                .pane(centerLayout())
                .build();

        SceneFactory sceneFactory = new SceneFactory(primaryStage);
        sceneFactory.makeScene(borderPaneApplicationLayout);
        primaryStage.show();
    }

    private Pane centerLayout() throws CoreException {
        VBox vBox = ControlsFactory.make(VBox.class, new VBoxOptions().name("Center"));
        vBox.setPadding(new Insets(15));

        LocationContext locationContext = new LocationContextOperator(vBox).getLocationContext();

        TextField nameField = ControlsFactory.make(TextField.class, locationContext, new TextFieldOptions()
                .promptText("fenxlib.demo.hello.prompt.text")
        );
        nameField.setMaxWidth(200);
        Label greeting = ControlsFactory.make(Label.class, locationContext);
        greeting.setStyle("-fx-font-size: 15pt ;");

        String worldTranslation = ResourceBundleServiceRegistry.getInstance().getMessage("fenxlib.demo.hello.default");
        String translatedMessage = ResourceBundleServiceRegistry.getInstance().getMessage("fenxlib.demo.hello.message", worldTranslation);
        greeting.setText(translatedMessage);

        StringProperty nameProperty = nameField.textProperty();

        nameProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String useValue = newValue;
                if (StringUtils.isBlank(newValue)) {
                    useValue = worldTranslation;
                }
                String translatedMessage = ResourceBundleServiceRegistry.getInstance().getMessage("fenxlib.demo.hello.message", useValue);
                greeting.setText(translatedMessage);
            }
        });


        vBox.getChildren().addAll(nameField, greeting);
        return vBox;
    }

    private MenuBar menuBar() throws CoreException {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new MenuBuilder()
                .name("fenxlib.demo.menu.label.file")
                .menuSection(new FileExitMenuSection())
                .build();
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

}
