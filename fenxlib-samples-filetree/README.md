# AboutPage Sample
## Usage
```java
//Any files added via import or filesystem watches on added directories will be added here
        FileTreeRegistry fileTreeRegistry = new FileTreeRegistry();

        //while watching file system, only auto-add folders and xml files
        FileWatchHandler fileWatchHandler = new FileWatchHandler.Builder()
                .fileFilter(new SuffixFileFilter(".xml"))
                .build(fileTreeRegistry);

        SimpleFileExplorer simpleFileExplorer = new SimpleFileExplorerFactory()
                .makeNode(new DefaultLocationContext("FileTree"), simpleFileExplorerOptions);

        BorderPaneApplicationLayout borderPaneApplicationLayout = new BorderPaneApplicationLayout.BorderPaneBuilder()
                .title("fenxlib.demo.filetree.title")
                //...
                .leftRegionOptions(new LeftRegionOptions(simpleFileExplorer))
                //...
                .build();
```
