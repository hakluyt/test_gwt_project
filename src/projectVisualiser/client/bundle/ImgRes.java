package projectVisualiser.client.bundle;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImgRes extends ClientBundle  {
	  
	  @Source("projectVisualiser/shared/blue_circ.png")
	  public ImageResource blue_circ();
	  
	  @Source("projectVisualiser/shared/red_circ.png")
	  public ImageResource red_circ();
	  
	  @Source("projectVisualiser/shared/green_circ.png")
	  public ImageResource green_circ();
}
