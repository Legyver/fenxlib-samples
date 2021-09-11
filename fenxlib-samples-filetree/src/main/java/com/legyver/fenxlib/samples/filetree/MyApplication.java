package com.legyver.fenxlib.samples.filetree;

import com.legyver.fenxlib.core.api.uimodel.IUiModel;
import com.legyver.fenxlib.core.impl.config.JsonApplicationConfig;
import com.legyver.fenxlib.core.impl.config.options.ApplicationOptions;
import com.legyver.fenxlib.core.impl.factory.*;
import com.legyver.fenxlib.core.impl.factory.menu.*;
import com.legyver.fenxlib.core.impl.factory.options.BorderPaneInitializationOptions;
import com.legyver.fenxlib.core.impl.factory.options.RegionInitializationOptions;
import com.legyver.fenxlib.extensions.tuktukfx.config.TaskExecutorShutdownApplicationLifecycleHook;
import com.legyver.fenxlib.widgets.filetree.factory.SimpleFileExplorerFactory;
import com.legyver.fenxlib.widgets.filetree.registry.FileTreeRegistry;
import com.legyver.fenxlib.widgets.filetree.scan.FileWatchHandler;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.io.filefilter.SuffixFileFilter;

public class MyApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        new ApplicationOptions.AutoStartBuilder<>()
                .appName("FenxlibFileTreeDemo")
                .customAppConfigInstantiator(JsonApplicationConfig::new)
                .uiModel(new ApplicationUIModel())
                //since we're using a thread pool to monitor the filesystem, shut down the thread pool on application exit
                //we use a delay matching the tryAcquire timeout in FileSystemWatchTaskProcessor#runUntilAbort() so we don't get an interrupted exception in the latter
                //since the task abort hook fires in a previous lifecycle phase to the thread pool shutdown, this is not strictly necessary, but better safe than sorry.
                .registerLifecycleHook(new TaskExecutorShutdownApplicationLifecycleHook())
                .build();

        SceneFactory sceneFactory = new SceneFactory(primaryStage, 600, 675, MyApplication.class.getResource("application.css"));

        //Any files added via import or filesystem watches on added directories will be added here
        FileTreeRegistry fileTreeRegistry = new FileTreeRegistry();

        //while watching file system, only auto-add folders and xml files
        FileWatchHandler fileWatchHandler = new FileWatchHandler.Builder()
                .fileFilter(new SuffixFileFilter(".xml"))
                .build(fileTreeRegistry);

        BorderPaneInitializationOptions options = new BorderPaneInitializationOptions.Builder()
                .center(new RegionInitializationOptions.Builder()
                        //popup will display over this. See the centerContentReference Supplier above
                        .factory(new StackPaneRegionFactory(true, new TextFactory("Right click on file explorer and choose 'Add' to add a directory")))
                )
                .top(new RegionInitializationOptions.Builder()
                        .factory(new TopRegionFactory(
                                new LeftMenuOptions(
                                        new MenuFactory("File",
                                                new ExitMenuItemFactory("Exit")
                                        )
                                ),
                                new CenterOptions(new TextFieldFactory(false)),
                                new RightMenuOptions())
                        ))
                .left(new RegionInitializationOptions.SideBuilder("Files")
                        .factory(new StackPaneRegionFactory(false, new SimpleFileExplorerFactory(fileTreeRegistry, fileWatchHandler))))
                .build();

        BorderPane root = new BorderPaneFactory(options).makeBorderPane();
        primaryStage.setScene(sceneFactory.makeScene(root));
        primaryStage.setTitle("File Explorer Demo");
        primaryStage.show();
    }

    private static class ApplicationUIModel implements IUiModel {

    }
}
