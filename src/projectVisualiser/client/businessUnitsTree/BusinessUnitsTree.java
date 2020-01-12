package projectVisualiser.client.businessUnitsTree;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

import projectVisualiser.client.model.BusinessUnit;

public class BusinessUnitsTree extends Tree {
		    
	public BusinessUnitsTree() {
		super();
	}

	public BusinessUnitsTree(Resources arrowTreeImg) {
		super (arrowTreeImg);
	}

	//update the Tree within the BusinessUnitsList
	public void updateTree (List <BusinessUnit> BusinessUnitList) {
		List <TreeItem> items = new ArrayList <>();
		//convert OrgUnit to TreeItem; add roots
		for (BusinessUnit orgUnit : BusinessUnitList) {
			TreeItem item = new TreeItem ();
			item.setUserObject(orgUnit);
			item.setText(orgUnit.getName());
			if (orgUnit.getParentID() == orgUnit.getId()) {
				this.addItem(item);
			}
			items.add(item);
		}
		//set relations between items
		for (TreeItem parentItem : items) {
			for (TreeItem possibleChild : items) {
				if (parentItem != possibleChild &&
						((BusinessUnit)parentItem.getUserObject()).getId() == ((BusinessUnit)possibleChild.getUserObject()).getParentID()) {
					parentItem.addItem(possibleChild);
				}
			}
		}
	}
		
}












