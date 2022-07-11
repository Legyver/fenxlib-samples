package com.legyver.fenxlib.samples.icon;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.api.config.options.ApplicationOptions;
import com.legyver.fenxlib.api.uimodel.IUiModel;
import com.legyver.fenxlib.config.json.JsonApplicationConfig;
import com.legyver.fenxlib.core.controls.factory.SceneFactory;
import com.legyver.fenxlib.core.icons.IconRegistry;
import com.legyver.fenxlib.core.layout.BorderPaneApplicationLayout;
import com.legyver.fenxlib.core.layout.options.CenterRegionOptions;
import com.legyver.fenxlib.core.menu.templates.MenuBuilder;
import com.legyver.fenxlib.core.menu.templates.section.FileExitMenuSection;
import com.legyver.fenxlib.icons.standard.IcoMoonFontEnum;
import com.legyver.fenxlib.icons.standard.IcoMoonIconOptions;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.scenicview.ScenicView;

public class MyApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationOptions applicationOptions = new ApplicationOptions.Builder<>()
                .appName("FenxlibIconDemo")
                .customAppConfigInstantiator(map -> new JsonApplicationConfig(map))
                .uiModel(new ApplicationUIModel())
                .styleSheetUrl(MyApplication.class.getResource("application.css"))
                .build();
        applicationOptions.startup(this, primaryStage);

        SceneFactory sceneFactory = new SceneFactory(primaryStage);

        BorderPaneApplicationLayout borderPaneApplicationLayout = new BorderPaneApplicationLayout.BorderPaneBuilder()
                .title("Fenxlib Icon Demo")
                .width(600.0)
                .height(800.0)
                .menuBar(menuBar())
                .centerRegionOptions(new CenterRegionOptions(centerLayout()))
                .build();
        Scene scene = sceneFactory.makeScene(borderPaneApplicationLayout);
        primaryStage.show();

//        ScenicView.show(scene);
    }

    private Node centerLayout() {
        StackPane stackPane = new StackPane();
        IcoMoonIconOptions iconOptions = new IcoMoonIconOptions.Builder()
                .icoMoonIcon(IcoMoonFontEnum.ICON_CODEPEN)
                .iconColorString("#ccc37c")
                .iconSize(192)
                .build();
        Text icon = IconRegistry.getInstance().getIcon(iconOptions);
        stackPane.getChildren().add(icon);
        return stackPane;
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
