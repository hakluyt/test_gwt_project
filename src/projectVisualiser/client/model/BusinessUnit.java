package projectVisualiser.client.model;
import java.io.Serializable;

public class BusinessUnit implements Serializable {
	
    private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int parentID;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
}
