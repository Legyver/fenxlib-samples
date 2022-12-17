module com.legyver.fenxlib.samples.hello {
    requires com.legyver.core;
    requires com.legyver.fenxlib.api;
    requires com.legyver.fenxlib.core;
    requires javafx.graphics;
    requires javafx.controls;
    requires org.apache.commons.lang3;//StringUtils

    exports com.legyver.fenxlib.samples.hello to javafx.graphics;
    opens com.legyver.fenxlib.samples.hello;//allows fenxlib to read your resource bundle

}