package projectVisualiser.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import projectVisualiser.client.model.BusinessUnit;
import projectVisualiser.client.model.Project;

public interface DBOperationServiceAsync {
	
	void getBUList(AsyncCallback<List <BusinessUnit>> callback);
	
	void getProjectsList(int buID, AsyncCallback<List<Project>> callback);

	void updateDB(int projrctID, String tablename, String newInfo, AsyncCallback<Void> callback);
	
}
