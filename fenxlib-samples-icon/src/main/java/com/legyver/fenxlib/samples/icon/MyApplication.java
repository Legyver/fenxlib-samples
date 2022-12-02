package com.legyver.fenxlib.samples.icon;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.api.config.options.ApplicationOptions;
import com.legyver.fenxlib.api.uimodel.IUiModel;
import com.legyver.fenxlib.core.controls.factory.SceneFactory;
import com.legyver.fenxlib.core.icons.IconRegistry;
import com.legyver.fenxlib.core.layout.BorderPaneApplicationLayout;
import com.legyver.fenxlib.core.layout.options.CenterRegionOptions;
import com.legyver.fenxlib.core.menu.templates.MenuBuilder;
import com.legyver.fenxlib.core.menu.templates.section.FileExitMenuSection;
import com.legyver.fenxlib.icons.standard.IcoMoonFontEnum;
import com.legyver.fenxlib.icons.standard.IcoMoonIconOptions;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.scenicview.ScenicView;

public class MyApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationOptions applicationOptions = new ApplicationOptions.Builder<>()
                .appName("FenxlibIconDemo")
                .uiModel(new ApplicationUIModel())
                .styleSheetUrl(MyApplication.class.getResource("application.css"))
                .resourceBundle("com.legyver.fenxlib.samples.icon.demo")
                .build();
        applicationOptions.startup(this, primaryStage);

        SceneFactory sceneFactory = new SceneFactory(primaryStage);

        BorderPaneApplicationLayout borderPaneApplicationLayout = new BorderPaneApplicationLayout.BorderPaneBuilder()
                .title("fenxlib.demo.title")
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
        VBox pane = new VBox();
        IcoMoonIconOptions iconOptions = new IcoMoonIconOptions.Builder()
                .icoMoonIcon(IcoMoonFontEnum.ICON_CODEPEN)
                .iconColorString("#ccc37c")
                .iconSize(192)
                .build();
        Text icon = IconRegistry.getInstance().getIcon(iconOptions);
        pane.getChildren().add(icon);
        Label label = new Label(IcoMoonFontEnum.ICON_ARROW_UP.getUnicode());
        label.setStyle("-fx-font-family: icomoon; -fx-font-size: 120;");
        pane.getChildren().add(label);
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(40);
        return pane;
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

    private static class ApplicationUIModel implements IUiModel {

    }
}
