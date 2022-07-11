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
