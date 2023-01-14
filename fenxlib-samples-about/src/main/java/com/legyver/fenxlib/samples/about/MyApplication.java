package com.legyver.fenxlib.samples.about;

import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.api.config.options.ApplicationOptions;
import com.legyver.fenxlib.api.context.ResourceScope;
import com.legyver.fenxlib.api.uimodel.IUiModel;
import com.legyver.fenxlib.core.controls.factory.SceneFactory;
import com.legyver.fenxlib.core.layout.BorderPaneApplicationLayout;
import com.legyver.fenxlib.core.layout.options.CenterRegionOptions;
import com.legyver.fenxlib.core.menu.templates.MenuBuilder;
import com.legyver.fenxlib.core.menu.templates.section.FileExitMenuSection;
import com.legyver.fenxlib.widgets.about.AboutMenuSection;
import com.legyver.fenxlib.widgets.about.AboutPage;
import com.legyver.fenxlib.widgets.about.AboutPageFactory;
import com.legyver.fenxlib.widgets.about.AboutPageOptions;

import javafx.scene.Scene;
import org.scenicview.ScenicView;

import javafx.application.Application;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;


public class MyApplication extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		ApplicationOptions applicationOptions = new ApplicationOptions.Builder<>()
				.appName("FenxlibAboutPageDemo")
				.uiModel(new ApplicationUIModel())
				.styleSheetUrl(MyApplication.class.getResource("application.css"), ResourceScope.APPLICATION)
				.styleSheetUrl(MyApplication.class.getResource("popups.css"), ResourceScope.POPUPS)
				.resourceBundle("com.legyver.fenxlib.samples.about.bundle")
				.build();
		applicationOptions.startup(this, primaryStage);

		SceneFactory sceneFactory = new SceneFactory(primaryStage);

		BorderPaneApplicationLayout borderPaneApplicationLayout = new BorderPaneApplicationLayout.BorderPaneBuilder()
				.title("fenxlib.demo.title")
				.width(600.0)
				.height(800.0)
				.menuBar(menuBar())
				.build();
		Scene scene = sceneFactory.makeScene(borderPaneApplicationLayout);
		primaryStage.show();

//		ScenicView.show(scene);
	}

	private AboutPage getAboutPage() throws CoreException {
		return new AboutPageFactory().makeNode(null, getAboutPageOptions());
	}

	private AboutPageOptions getAboutPageOptions() {
		return new AboutPageOptions.Builder(getClass())
				.buildPropertiesFile("build.properties")
				.copyrightPropertiesFile("copyright.properties")
				.title("fenxlib.demo.about.title")
				.intro("fenxlib.demo.about.intro")
				.gist("fenxlib.demo.about.gist")
				.additionalInfo("fenxlib.demo.about.additionalInfo")
				.build();
	}

	private MenuBar menuBar() throws CoreException {
		MenuBar menuBar = new MenuBar();

		Menu fileMenu = new MenuBuilder()
				.name("fenxlib.demo.menu.label.file")
				.menuSection(new FileExitMenuSection())
				.build();
		menuBar.getMenus().add(fileMenu);

		AboutPageOptions aboutPageOptions = getAboutPageOptions();

		Menu helpMenu = new MenuBuilder()
				.name("fenxlib.demo.menu.label.help")
				.menuSection(new AboutMenuSection(aboutPageOptions))
				.build();
		menuBar.getMenus().add(helpMenu);

		return menuBar;
	}

	private static class ApplicationUIModel implements IUiModel {

	}


}
