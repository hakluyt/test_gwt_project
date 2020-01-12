package projectVisualiser.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import projectVisualiser.client.model.BusinessUnit;
import projectVisualiser.client.model.Project;

@RemoteServiceRelativePath("DBOperationService")
public interface DBOperationService extends RemoteService{
	List <BusinessUnit> getBUList ();
	
	List <Project> getProjectsList (int buID);
	
	void updateDB (int projrctID, String tablename, String newInfo);
	
}
