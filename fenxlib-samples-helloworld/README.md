# Hello World Sample
- i18n
  - menus
  - labels
  - prompt text
- Input form to have "Hello World" changed to "Hello (Name)"

## Usage
### Initialize the framework

```java
public class MyApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties buildProperties = new Properties();
        buildProperties.load(MyApplication.class.getResourceAsStream("/build.properties"));//application version
        ApplicationOptions applicationOptions = new ApplicationOptions.Builder<>()
                .appName("Hello World")
                .buildProperties(buildProperties)
                .resourceBundle("com.legyver.fenxlib.samples.hello.labels")//bundle with all text and translations
                .build();
        applicationOptions.startup(this, primaryStage);
        //...
    }
    //...
}
```

### Setup the layout
```java
public class MyApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //...
        SinglePaneApplicationLayout borderPaneApplicationLayout = new SinglePaneApplicationLayout.SinglePaneApplicationLayoutBuilder()
                .title("fenxlib.demo.title")//i18n window title
                .width(600.0)
                .height(800.0)
                .menuBar(menuBar())
                .pane(centerLayout())
                .build();
        //...
    }
    //..
}
```

### Setup your menus
```java
public class MyApplication extends Application {
    //...
    private MenuBar menuBar() throws CoreException {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new MenuBuilder()
                .name("fenxlib.demo.menu.label.file")//i18n property
                .menuSection(new FileExitMenuSection())//Any out-of-the-box menus are automatically i18n
                .build();
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }
}
```

### Setup controls
```java
public class MyApplication extends Application {
    //...
    private Pane centerLayout() throws CoreException {
        //the name() is for registering.  If no name is specified, the framework generates a UUID
        VBox vBox = ControlsFactory.make(VBox.class, new VBoxOptions().name("Center"));

        //we don't actually need the location context in this example, but this is how you could retrieve it should you need to
        LocationContext locationContext = new LocationContextOperator(vBox).getLocationContext();

        //providing the location context of the vbox means that the text field will be registered under the vbox
        //this could be help full if you only know the vbox location, you could look up the text field in that vbox
        TextField nameField = ControlsFactory.make(TextField.class, locationContext, new TextFieldOptions()
                .promptText("fenxlib.demo.hello.prompt.text")//i18n prompt text
        );
        nameField.setMaxWidth(200);
        //providing the vbox location context means that the label will be registered under the vbox
        Label greeting = ControlsFactory.make(Label.class, locationContext);
        greeting.setStyle("-fx-font-size: 15pt ;");
        
        //look up a translation for the default "Hello World" message
        String translatedMessage = ResourceBundleServiceRegistry.getInstance().getMessage("fenxlib.demo.hello.message", "World");
        greeting.setText(translatedMessage);

        //When the name field has text entered, re-interpret the message with that text
        StringProperty nameProperty = nameField.textProperty();
        nameProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String useValue = newValue;
                if (StringUtils.isBlank(newValue)) {
                    useValue = "World";
                }
                String translatedMessage = ResourceBundleServiceRegistry.getInstance().getMessage("fenxlib.demo.hello.message", useValue);
                greeting.setText(translatedMessage);
            }
        });
        
        //add the two new controls to the vbox
        vBox.getChildren().addAll(nameField, greeting);
        return vBox;
    }

}
```