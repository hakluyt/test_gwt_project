package projectVisualiser.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import projectVisualiser.client.model.BusinessUnit;
import projectVisualiser.client.model.Project;
import projectVisualiser.client.service.DBOperationService;

public class DBOperationServiceImpl extends RemoteServiceServlet implements DBOperationService {

	private static final long serialVersionUID = 1L;
	
	private DBConnector dbConnector = new DBConnector();

	public void setDbConnector(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	@Override
	public List<BusinessUnit> getBUList() {
		List<BusinessUnit> buList = new ArrayList <>();
		Connection connection = dbConnector.getConnection();
		try (Statement statement = connection.createStatement()) {
			String sql = "SELECT id, name, parent_id FROM business_units";
			ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
            	BusinessUnit businUnit = new BusinessUnit();
            	businUnit.setId(resultSet.getInt(1));
            	businUnit.setName(resultSet.getString(2));
            	businUnit.setParentID(resultSet.getInt(3));
            	buList.add(businUnit);
            }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return buList;
	}

	@Override
	public List<Project> getProjectsList(int buID) {
		List <Project> projectsList = new ArrayList <>();
		Connection connection = dbConnector.getConnection();
		try (Statement statement = connection.createStatement()) {
			String sql = String.format("SELECT id, name, priority, start_date, end_date, description FROM projects WHERE bu_id = %d", buID);
			ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
            	Project project = new Project();
            	project.setId(resultSet.getInt(1));
            	project.setName(resultSet.getString(2));
            	project.setPriority(resultSet.getInt(3));
            	project.setStartDate(resultSet.getTimestamp(4));
            	project.setEndDate(resultSet.getTimestamp(5));
            	project.setDescription(resultSet.getString(6));
            	projectsList.add(project);
            }
            
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return projectsList;
	}

	@Override
	public void updateDB(int projectID, String tablename, String newData) {
		Connection connection = dbConnector.getConnection();
		try (Statement statement = connection.createStatement()) {
			if (newData.equals("null") && tablename.equals("end_date")) {//give permission to erase end date
				String sql = String.format("UPDATE projects SET %s = null WHERE id = %d", tablename, projectID);
				statement.execute(sql);
			} else {
				String sql = String.format("UPDATE projects SET %s = '%s' WHERE id = %d", tablename, newData, projectID);
				statement.execute(sql);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
}
}



