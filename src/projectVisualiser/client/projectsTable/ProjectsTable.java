package projectVisualiser.client.projectsTable;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import projectVisualiser.client.bundle.ImgRes;
import projectVisualiser.client.model.Project;
import projectVisualiser.client.service.DBOperationService;
import projectVisualiser.client.service.DBOperationServiceAsync;

public class ProjectsTable extends FlexTable {
	
	private DBOperationServiceAsync dbService;
	private ImgRes imgRes = GWT.create(ImgRes.class);
	
	//handler removes a focus from the textbox widget when Enter is pressed
	private KeyDownHandler pressEnterHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				TextBox sender = (TextBox) event.getSource();
				sender.setFocus(false);
			}
		}
	};
	
	public ProjectsTable (DBOperationServiceAsync dbService) {
		
		this.dbService = dbService;
		
        this.setWidth("95%");
        this.setBorderWidth(1);
        this.setCellSpacing (0);
        
		this.setText(0, 0, "Имя");
		this.setText(0, 1, "Приоритет");
		this.setText(0, 2, "Дата Начала");
		this.setText(0, 3, "Конечная Дата");
		this.setText(0, 4, "Описание");
	}
	
	public void updateTable (List <Project> projects) {
		
		if (dbService == null) {
			dbService = GWT.create(DBOperationService.class);
		}
				
		//remove the table content
		for (int i = 1; i < this.getRowCount(); i++) {
			this.removeRow(i);
		}
		
		
		//fill the table with the projects data
		int rowCounter = 1;
		for (Project project : projects) {
			
			int projectID = project.getId();
			
			//init name cell
			HorizontalPanel nameCell = initNameCell(project);
			this.setWidget(rowCounter, 0, nameCell);
			
			//init priority cell
			ListBox priorityCell = new ListBox();
			int projectPriority = project.getPriority();
			priorityCell.addItem("Срочный");
			priorityCell.addItem("Высокий");
			priorityCell.addItem("Нормальный");
			priorityCell.addItem("Низкий");
			priorityCell.setItemSelected(projectPriority-1, true);
			priorityCell.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					
					AsyncCallback <Void> callback = new AsyncCallback <Void> (){

						@Override
						public void onFailure(Throwable caught) {
							priorityCell.setItemSelected(projectPriority-1, true);	
							Window.alert("Не удалось изменить данные в базе");	
						}

						@Override
						public void onSuccess(Void result) {
							
						}
					};
					dbService.updateDB(projectID, "priority", String.valueOf(priorityCell.getSelectedIndex()+1), callback);
				}
			});
			this.setWidget(rowCounter, 1, priorityCell);
			
			//date pattern
			DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm");			
		
			//init start date cell
			TextBox startDateCell = new TextBox();
			String startDateString = project.getStartDate() == null? "" : dateTimeFormat.format(project.getStartDate());
			startDateCell.setText(startDateString);
			startDateCell.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					String changedDate = startDateCell.getText();
					if (checkDate (changedDate)) {
						AsyncCallback <Void> callback = new AsyncCallback <Void> (){

							@Override
							public void onFailure(Throwable caught) {
								startDateCell.setText(startDateString);			
								Window.alert("Не удалось изменить данные в базе");	
							}

							@Override
							public void onSuccess(Void result) {
								
							}
						};
						dbService.updateDB(projectID, "start_date", changedDate, callback);					
					}
					else {
						startDateCell.setText(startDateString);
						Window.alert("Введите корректную дату в формате: \"dd.MM.yyyy HH:mm\"");	
					}
				}
			});
			startDateCell.addKeyDownHandler(pressEnterHandler);
			this.setWidget(rowCounter, 2, startDateCell);
			
			//init end date cell
			TextBox endDateCell = new TextBox();
			String endDateString = project.getEndDate() == null? "" : dateTimeFormat.format(project.getEndDate());
			endDateCell.setText(endDateString);
			endDateCell.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					String changedDate = endDateCell.getText();
					if (checkDate (changedDate) || changedDate == "") {
						AsyncCallback <Void> callback = new AsyncCallback <Void> (){

							@Override
							public void onFailure(Throwable caught) {
								endDateCell.setText(endDateString);		
								Window.alert("Не удалось изменить данные в базе");	
							}

							@Override
							public void onSuccess(Void result) {

							}
						};
						if (changedDate == "") changedDate = "null";
						dbService.updateDB(projectID, "end_date", changedDate, callback);							
					}
					else {
						endDateCell.setText(endDateString);
						Window.alert("Введите корректную дату в формате: \"dd.MM.yyyy HH:mm\"");	
					}
				}
			});
			endDateCell.addKeyDownHandler(pressEnterHandler);
			this.setWidget(rowCounter, 3, endDateCell);
	
			//init description cell
			TextBox descripionCell = new TextBox();
			String projectDescription = project.getDescription();
			descripionCell.setText(projectDescription);
			descripionCell.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					AsyncCallback <Void> callback = new AsyncCallback <Void> (){

						@Override
						public void onFailure(Throwable caught) {
							descripionCell.setText(projectDescription);					
						}

						@Override
						public void onSuccess(Void result) {

						}
					};
					
					dbService.updateDB(projectID, "description", descripionCell.getText(), callback);
				}
			});
			descripionCell.addKeyDownHandler(pressEnterHandler);
			this.setWidget(rowCounter, 4, descripionCell);
			
			rowCounter++;
		}
	}
	
	private HorizontalPanel initNameCell (Project project) {
		HorizontalPanel namePanel = new HorizontalPanel();
				
		Image pickedImg = new Image (imgRes.red_circ());
		if (project.getEndDate() == null) {
			if ((project.getStartDate().getTime() - (System.currentTimeMillis())) >= 0) {
				pickedImg = new Image (imgRes.blue_circ());
			}
			else pickedImg = new Image (imgRes.green_circ());
		}
		
		pickedImg.setPixelSize(18, 18);
		namePanel.add(pickedImg);
		
		TextBox nameBox = new TextBox();
		String projectName = project.getName();
		nameBox.setText(projectName);
		nameBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				
				AsyncCallback <Void> callback = new AsyncCallback <Void> (){

					@Override
					public void onFailure(Throwable caught) {
						nameBox.setText(projectName);					
					}

					@Override
					public void onSuccess(Void result) {

					}
				};
				dbService.updateDB(project.getId(), "name", nameBox.getText(), callback);
			}
		});
		nameBox.addKeyDownHandler(pressEnterHandler);
		
		namePanel.add(nameBox);
		return namePanel;
	}
	
	private boolean checkDate (String date) {
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm");
		try {
		dateTimeFormat.parse(date);
		return true;
		}
		catch (Exception parseExc) {
		return false;
		}
	}
		
}




