module com.legyver.fenxlib.samples.about {
	requires com.legyver.fenxlib.api;
	requires com.legyver.fenxlib.core;
	requires com.legyver.fenxlib.widgets.about;
	requires javafx.graphics;
	requires javafx.controls;

	exports com.legyver.fenxlib.samples.about to javafx.graphics;
	opens com.legyver.fenxlib.samples.about;
}