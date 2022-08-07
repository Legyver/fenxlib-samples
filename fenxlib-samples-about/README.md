# AboutPage Sample
## Usage
```java
AboutPageOptions aboutPageOptions = new AboutPageOptions.Builder(getClass())
    .buildPropertiesFile("build.properties")
    .copyrightPropertiesFile("copyright.properties")
        //below are resource bundle properties evaluated at runtime for i18n
    .title("fenxlib.demo.about.title")
    .intro("fenxlib.demo.about.intro")
    .gist("fenxlib.demo.about.gist")
    .additionalInfo("fenxlib.demo.about.additionalInfo")
    .build();
```
## Result
![About Page](https://user-images.githubusercontent.com/3435255/107864578-92c58780-6e2b-11eb-8b87-5beee11504d0.png)