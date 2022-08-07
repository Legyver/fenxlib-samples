# Snackbar Sample

## Usage
```java
ApplicationOptions applicationOptions = new ApplicationOptions.Builder<>()
        //...
        //specify where to display each level of alert
        //if left unspecified, they will all display in the bottom-right of the application
        .displayAlerts(Level.ERROR, IAlert.TargetRegion.APPLICATION_BOTTOM_RIGHT)
        .displayAlerts(Level.WARNING, IAlert.TargetRegion.APPLICATION_BOTTOM_RIGHT)
        .displayAlerts(Level.INFO, IAlert.TargetRegion.APPLICATION_TOP_RIGHT)
        //hook up our i18n resource bundle
        .resourceBundle("com.legyver.fenxlib.samples.alerts.demo")
        .build();

AlertGeneratingForm alertGeneratingForm = new AlertGeneratingForm();
alertGeneratingForm.valueProperty().addListener((observable, oldValue, newValue) -> {
    if (NumberUtils.isDigits(newValue)) {
        ApplicationContext.infoAlert(new AlertTextContent("fenxlib.demo.ok.message", newValue), 1000L);
    } else if (NumberUtils.isParsable(newValue)) {
        ApplicationContext.warningAlert(new AlertTextContent("fenxlib.demo.warning.message", newValue), 2000L);
    } else {
        ApplicationContext.errorAlert(new AlertTextContent("fenxlib.demo.error.message", newValue));
    }
});
```
