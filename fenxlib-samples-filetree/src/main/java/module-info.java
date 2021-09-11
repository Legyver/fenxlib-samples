module com.legyver.fenxlib.samples.filetree {
	requires com.legyver.fenxlib.core.impl;
	requires com.legyver.fenxlib.widgets.filetree;
	requires org.apache.commons.io;
	requires com.legyver.fenxlib.extensions.tuktukfx;

	exports com.legyver.fenxlib.samples.filetree to javafx.graphics;
	opens com.legyver.fenxlib.samples.filetree;
}