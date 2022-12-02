package com.legyver.fenxlib.samples.filetree;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.api.config.options.ApplicationOptions;
import com.legyver.fenxlib.api.controls.ControlsFactory;
import com.legyver.fenxlib.api.locator.DefaultLocationContext;
import com.legyver.fenxlib.api.scene.text.options.TextOptions;
import com.legyver.fenxlib.api.uimodel.IUiModel;
import com.legyver.fenxlib.widgets.filetree.config.FileTreeConfig;
import com.legyver.fenxlib.core.controls.factory.SceneFactory;
import com.legyver.fenxlib.core.layout.BorderPaneApplicationLayout;
import com.legyver.fenxlib.core.layout.options.CenterRegionOptions;
import com.legyver.fenxlib.core.layout.options.LeftRegionOptions;
import com.legyver.fenxlib.core.menu.templates.MenuBuilder;
import com.legyver.fenxlib.core.menu.templates.section.FileExitMenuSection;
import com.legyver.fenxlib.widgets.filetree.SimpleFileExplorer;
import com.legyver.fenxlib.widgets.filetree.factory.SimpleFileExplorerFactory;
import com.legyver.fenxlib.widgets.filetree.factory.SimpleFileExplorerOptions;
import com.legyver.fenxlib.widgets.filetree.menu.ImportMenuSection;
import com.legyver.fenxlib.widgets.filetree.registry.FileTreeRegistry;
import com.legyver.fenxlib.widgets.filetree.scan.FileWatchHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyApplication extends Application {
    private static Logger logger;
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationOptions applicationOptions = new ApplicationOptions.Builder<>()
                .appName("FenxlibFileTreeDemo")
                //FileTreeConfig implements FileTreeConfigAware, so we will persist the records
                //of the last file set displayed in the tree for retrieval the next time the application
                //starts up
                .applicationConfigClass(FileTreeConfig.class)
                .uiModel(new ApplicationUIModel())
                .styleSheetUrl(MyApplication.class.getResource("application.css"))
                .resourceBundle("com.legyver.fenxlib.samples.filetree.demo")
                .build();

        applicationOptions.startup(this, primaryStage);

        SceneFactory sceneFactory = new SceneFactory(primaryStage);

        //Any files added via import or filesystem watches on added directories will be added here
        FileTreeRegistry fileTreeRegistry = new FileTreeRegistry();

        //while watching file system, only auto-add folders and xml files
        FileWatchHandler fileWatchHandler = new FileWatchHandler.Builder()
                .fileFilter(new SuffixFileFilter(".xml"))
                .build(fileTreeRegistry);

        SimpleFileExplorerOptions simpleFileExplorerOptions = new SimpleFileExplorerOptions()
                .fileTreeRegistry(fileTreeRegistry)
                .fileWatchHandler(fileWatchHandler);

        SimpleFileExplorer simpleFileExplorer = new SimpleFileExplorerFactory()
                .makeNode(new DefaultLocationContext("FileTree"), simpleFileExplorerOptions);

        StackPane stackPane = ControlsFactory.make(StackPane.class);
        Text text = ControlsFactory.make(Text.class, new TextOptions()
                .text("fenxlib.demo.filetree.center.pane.label.usage.hint"));
        stackPane.getChildren().add(text);

        BorderPaneApplicationLayout borderPaneApplicationLayout = new BorderPaneApplicationLayout.BorderPaneBuilder()
                .title("fenxlib.demo.filetree.title")
                .width(1000.0)
                .height(800.0)
                .menuBar(menuBar(fileTreeRegistry))
                .leftRegionOptions(new LeftRegionOptions(simpleFileExplorer))
                .centerRegionOptions(new CenterRegionOptions(stackPane))
                .build();
        Scene scene = sceneFactory.makeScene(borderPaneApplicationLayout);
        logger = LogManager.getLogger(MyApplication.class);
        logger.info("Application started.  Showing now.");
        primaryStage.show();
//        logger.info("Application started.  Launching scenic view.");
//        ScenicView.show(scene);
    }

    private MenuBar menuBar(FileTreeRegistry fileTreeRegistry) throws CoreException {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new MenuBuilder()
                .name("fenxlib.demo.filetree.menu.label.file")
                .menuSection(new ImportMenuSection(fileTreeRegistry))
                .menuSection(new FileExitMenuSection())
                .build();
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    private static class ApplicationUIModel implements IUiModel {

    }
}
