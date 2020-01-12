package projectVisualiser.client.entrypoint;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

import projectVisualiser.client.bundle.ArrowTreeImgRes;
import projectVisualiser.client.businessUnitsTree.BusinessUnitsTree;
import projectVisualiser.client.model.BusinessUnit;
import projectVisualiser.client.model.Project;
import projectVisualiser.client.projectsTable.ProjectsTable;
import projectVisualiser.client.service.DBOperationService;
import projectVisualiser.client.service.DBOperationServiceAsync;

public class ProjectVisualiser implements EntryPoint {
	
	private Tree.Resources arrowTreeImg = GWT.create(ArrowTreeImgRes.class);
	private DBOperationServiceAsync dbService = GWT.create(DBOperationService.class);
    private SplitLayoutPanel mainPanel = new SplitLayoutPanel();
    private BusinessUnitsTree businUnitsTree = new BusinessUnitsTree(arrowTreeImg);
    private ProjectsTable projectsTable = new ProjectsTable(dbService);
   
	@Override
	public void onModuleLoad() {
		RootLayoutPanel.get().add(mainPanel);
		fillBUTree();
		mainPanel.addWest(businUnitsTree, 256);
		mainPanel.add(projectsTable);
		
		businUnitsTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			//get the projects of the selected businessUnit
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				BusinessUnit selectedUnit = (BusinessUnit)(event.getSelectedItem().getUserObject());
				updateProjectTable(selectedUnit.getId());
			}  
		});	

	}
	
	private void fillBUTree() {
		if (dbService == null) {
		dbService = GWT.create(DBOperationService.class);
		}
		
		AsyncCallback<List <BusinessUnit>> callback = new AsyncCallback <List <BusinessUnit>> () {

			@Override
			public void onFailure(Throwable caught) {
							
			}

			@Override
			public void onSuccess(List<BusinessUnit> result) {
				businUnitsTree.updateTree(result);
			}
		};
		
		dbService.getBUList(callback);
	}
	

	private void updateProjectTable(int buID) {
		if (dbService == null) {
			dbService = GWT.create(DBOperationService.class);
		}
	
		AsyncCallback<List<Project>> callback = new AsyncCallback<List<Project>> () {

			@Override
			public void onFailure(Throwable caught) {
							
			}

			@Override
			public void onSuccess(List<Project> result) {
				projectsTable.updateTable(result);
			}
		};
		
		dbService.getProjectsList(buID, callback);
	}
	
}





	