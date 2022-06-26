package com.legyver.fenxlib.samples.about;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.api.config.options.ApplicationOptions;
import com.legyver.fenxlib.api.uimodel.IUiModel;
import com.legyver.fenxlib.config.json.JsonApplicationConfig;
import com.legyver.fenxlib.core.controls.factory.SceneFactory;
import com.legyver.fenxlib.core.layout.BorderPaneApplicationLayout;
import com.legyver.fenxlib.core.menu.templates.MenuBuilder;
import com.legyver.fenxlib.core.menu.templates.section.FileExitMenuSection;
import com.legyver.fenxlib.widgets.about.AboutMenuSection;
import com.legyver.fenxlib.widgets.about.AboutPageOptions;
import javafx.application.Application;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class MyApplication extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		ApplicationOptions applicationOptions = new ApplicationOptions.Builder<>()
				.appName("FenxlibAboutPageDemo")
				.customAppConfigInstantiator(map -> new JsonApplicationConfig(map))
				.uiModel(new ApplicationUIModel())
				.build();
		applicationOptions.startup(this, primaryStage);

		SceneFactory sceneFactory = new SceneFactory(primaryStage, MyApplication.class.getResource("application.css"));

		BorderPaneApplicationLayout borderPaneApplicationLayout = new BorderPaneApplicationLayout.BorderPaneBuilder()
				.title("Fenxlib About Page Demo")
				.width(600.0)
				.height(800.0)
				.menuBar(menuBar())
				.build();
		sceneFactory.makeScene(borderPaneApplicationLayout);
		primaryStage.show();
	}

	private MenuBar menuBar() throws CoreException {
		MenuBar menuBar = new MenuBar();

		Menu fileMenu = new MenuBuilder()
				.name("File")
				.menuSection(new FileExitMenuSection())
				.build();
		menuBar.getMenus().add(fileMenu);

		AboutPageOptions aboutPageOptions = new AboutPageOptions.Builder(getClass())
				.buildPropertiesFile("build.properties")
				.copyrightPropertiesFile("copyright.properties")
				.title("MyApplication")
				.intro("MyApplication makes amazing things easy")
				.gist("More stuff about how great this app is.  I can go on about it for a really long time and the text will wrap around.")
				.additionalInfo("be sure to tell your friends")
				.build();
		Menu helpMenu = new MenuBuilder()
				.name("Help")
				.menuSection(new AboutMenuSection(aboutPageOptions))
				.build();
		menuBar.getMenus().add(helpMenu);

		return menuBar;
	}

	private static class ApplicationUIModel implements IUiModel {

	}


}
