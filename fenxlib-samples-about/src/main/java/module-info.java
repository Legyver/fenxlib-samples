module com.legyver.fenxlib.samples.about {
	requires com.legyver.fenxlib.core.impl;
	requires com.legyver.fenxlib.widgets.about;

    exports com.legyver.fenxlib.samples.about to javafx.graphics;
	opens com.legyver.fenxlib.samples.about;
}