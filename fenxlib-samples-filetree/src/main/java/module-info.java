module com.legyver.fenxlib.samples.filetree {
	requires com.legyver.fenxlib.core;
	requires com.legyver.fenxlib.widgets.filetree;
	requires org.apache.commons.io;
	requires com.legyver.fenxlib.extensions.tuktukfx;
	requires com.legyver.fenxlib.api;
	requires com.legyver.fenxlib.config.json;
	requires javafx.graphics;
	requires javafx.controls;

	exports com.legyver.fenxlib.samples.filetree to javafx.graphics;
	opens com.legyver.fenxlib.samples.filetree;
}