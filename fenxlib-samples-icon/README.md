# Icon Sample
## Usage
```java
 IcoMoonIconOptions iconOptions = new IcoMoonIconOptions.Builder()
        .icoMoonIcon(IcoMoonFontEnum.ICON_CODEPEN)
        .iconColorString("#ccc37c")
        .iconSize(96)
        .build();
Text icon = IconRegistry.getInstance().getIcon(iconOptions);
```
Or 
```java
Label label = new Label(IcoMoonFontEnum.ICON_ARROW_UP.getUnicode());
label.setStyle("-fx-font-family: icomoon; -fx-font-size: 120;"); 
```