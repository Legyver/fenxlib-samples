module com.legyver.fenxlib.samples.alerts {
    requires org.apache.commons.lang3;

    requires javafx.controls;
    requires transitive javafx.web;
    requires transitive javafx.swing;

    requires com.legyver.fenxlib.api;
    requires com.legyver.fenxlib.core;
    requires org.apache.logging.log4j;
    requires org.scenicview.scenicview;
//    requires org.scenicview.scenicview;

    exports com.legyver.fenxlib.samples.alerts to javafx.graphics;
}