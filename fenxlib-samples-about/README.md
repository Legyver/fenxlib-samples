#AboutPage Sample
##Usage
```java
AboutPageOptions aboutPageOptions = new AboutPageOptions.Builder(getClass())
    .buildPropertiesFile("build.properties")
    .copyrightPropertiesFile("copyright.properties")
    .title("MyApplication")
    .intro("MyApplication makes amazing things easy")
    .gist("More stuff about how great this app is.  I can go on about it for a really long time and the text will wrap around.")
    .additionalInfo("be sure to tell your friends")
    .build();
```
##Result
