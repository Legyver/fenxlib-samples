# AboutPage Sample
## Usage
```java
//Any files added via import or filesystem watches on added directories will be added here
        FileTreeRegistry fileTreeRegistry = new FileTreeRegistry();

        //while watching file system, only auto-add folders and xml files
        FileWatchHandler fileWatchHandler = new FileWatchHandler.Builder()
                .fileFilter(new SuffixFileFilter(".xml"))
                .build(fileTreeRegistry);

        BorderPaneInitializationOptions options = new BorderPaneInitializationOptions.Builder()
              ...
                .left(new RegionInitializationOptions.SideBuilder("Files")
                      .factory(new StackPaneRegionFactory(false, new SimpleFileExplorerFactory(fileTreeRegistry, fileWatchHandler))))
                .build();
```
