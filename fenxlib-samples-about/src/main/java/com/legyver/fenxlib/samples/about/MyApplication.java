package com.legyver.fenxlib.samples.about;

import com.legyver.fenxlib.core.api.locator.query.ComponentQuery;
import com.legyver.fenxlib.core.api.uimodel.IUiModel;
import com.legyver.fenxlib.core.impl.config.JsonApplicationConfig;
import com.legyver.fenxlib.core.impl.config.options.ApplicationOptions;
import com.legyver.fenxlib.core.impl.factory.*;
import com.legyver.fenxlib.core.impl.factory.menu.*;
import com.legyver.fenxlib.core.impl.factory.options.BorderPaneInitializationOptions;
import com.legyver.fenxlib.core.impl.factory.options.RegionInitializationOptions;
import com.legyver.fenxlib.widgets.about.AboutMenuItemFactory;
import com.legyver.fenxlib.widgets.about.AboutPageOptions;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.Supplier;

public class MyApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		new ApplicationOptions.AutoStartBuilder<>()
				.appName("FenxlibAboutPageDemo")
				.customAppConfigInstantiator(map -> new JsonApplicationConfig(map))
				.uiModel(new ApplicationUIModel())
				.build();

		AboutPageOptions aboutPageOptions = new AboutPageOptions.Builder(getClass())
				.buildPropertiesFile("build.properties")
				.copyrightPropertiesFile("copyright.properties")
				.title("MyApplication")
				.intro("MyApplication makes amazing things easy")
				.gist("More stuff about how great this app is.  I can go on about it for a really long time and the text will wrap around.")
				.additionalInfo("be sure to tell your friends")
				.build();

		SceneFactory sceneFactory = new SceneFactory(primaryStage, 600, 675, MyApplication.class.getResource("application.css"));

		//where to display the popup over
		Supplier<StackPane> centerContentReference = () -> {
			Optional<StackPane> center = new ComponentQuery.QueryBuilder()
					.inRegion(BorderPaneInitializationOptions.REGION_CENTER)
					.type(StackPane.class).execute();
			return center.get();
		};

		BorderPaneInitializationOptions options = new BorderPaneInitializationOptions.Builder()
				.center(new RegionInitializationOptions.Builder()
						//popup will display over this. See the centerContentReference Supplier above
						.factory(new StackPaneRegionFactory(true, new TextFactory("Hello World")))
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
								))
						))
				.build();

		BorderPane root = new BorderPaneFactory(options).makeBorderPane();
		primaryStage.setScene(sceneFactory.makeScene(root));
		primaryStage.setTitle("About Page Demo");
		primaryStage.show();
	}

	private static class ApplicationUIModel implements IUiModel {

	}
}
