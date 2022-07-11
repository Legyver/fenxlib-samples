package com.legyver.fenxlib.samples.filetree;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.api.config.options.ApplicationOptions;
import com.legyver.fenxlib.api.locator.DefaultLocationContext;
import com.legyver.fenxlib.api.uimodel.IUiModel;
import com.legyver.fenxlib.config.json.JsonApplicationConfig;
import com.legyver.fenxlib.core.controls.ControlsFactory;
import com.legyver.fenxlib.core.controls.factory.SceneFactory;
import com.legyver.fenxlib.core.layout.BorderPaneApplicationLayout;
import com.legyver.fenxlib.core.layout.options.CenterRegionOptions;
import com.legyver.fenxlib.core.layout.options.LeftRegionOptions;
import com.legyver.fenxlib.core.menu.templates.MenuBuilder;
import com.legyver.fenxlib.core.menu.templates.section.FileExitMenuSection;
import com.legyver.fenxlib.core.scene.text.options.TextOptions;
import com.legyver.fenxlib.extensions.tuktukfx.config.TaskExecutorShutdownApplicationLifecycleHook;
import com.legyver.fenxlib.widgets.filetree.SimpleFileExplorer;
import com.legyver.fenxlib.widgets.filetree.factory.SimpleFileExplorerFactory;
import com.legyver.fenxlib.widgets.filetree.factory.SimpleFileExplorerOptions;
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
import org.scenicview.ScenicView;

public class MyApplication extends Application {
    private static Logger logger;
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationOptions applicationOptions = new ApplicationOptions.Builder<>()
                .appName("FenxlibFileTreeDemo")
                .customAppConfigInstantiator(JsonApplicationConfig::new)
                .uiModel(new ApplicationUIModel())
                .styleSheetUrl(MyApplication.class.getResource("application.css"))
                //since we're using a thread pool to monitor the filesystem, shut down the thread pool on application exit
                //we use a delay matching the tryAcquire timeout in FileSystemWatchTaskProcessor#runUntilAbort() so we don't get an interrupted exception in the latter
                //since the task abort hook fires in a previous lifecycle phase to the thread pool shutdown, this is not strictly necessary, but better safe than sorry.
                .registerLifecycleHook(new TaskExecutorShutdownApplicationLifecycleHook(2000))
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

        SimpleFileExplorer simpleFileExplorer = new SimpleFileExplorerFactory().makeNode(new DefaultLocationContext("File Tree"), simpleFileExplorerOptions);

        StackPane stackPane = ControlsFactory.make(StackPane.class);
        Text text = ControlsFactory.make(Text.class, new TextOptions()
                .text("Right click on file explorer and choose 'Add' to add a directory"));
        stackPane.getChildren().add(text);

        BorderPaneApplicationLayout borderPaneApplicationLayout = new BorderPaneApplicationLayout.BorderPaneBuilder()
                .title("Fenxlib File Tree Demo")
                .width(600.0)
                .height(800.0)
                .menuBar(menuBar())
                .leftRegionOptions(new LeftRegionOptions(simpleFileExplorer))
                .centerRegionOptions(new CenterRegionOptions(stackPane))
                .build();
        Scene scene = sceneFactory.makeScene(borderPaneApplicationLayout);
        logger = LogManager.getLogger(MyApplication.class);
        logger.info("Application started.  Showing now.");
        primaryStage.show();
        logger.info("Application started.  Launching scenic view.");

        ScenicView.show(scene);
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
