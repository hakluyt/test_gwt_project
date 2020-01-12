package projectVisualiser.client.bundle;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Tree;

public interface ArrowTreeImgRes extends Tree.Resources {
	
	@Source("projectVisualiser/shared/arrow_right.png")
	ImageResource treeClosed();
	
	@Source("projectVisualiser/shared/arrow_down.png")
	ImageResource treeOpen();

}
