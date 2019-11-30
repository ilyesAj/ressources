package isty.iatic5.arlo.res.view;

import java.util.ArrayList;
import java.util.List;

import isty.iatic5.arlo.res.facade.ResourcesInterface;
import isty.iatic5.arlo.res.model.InvalidInputException;
import isty.iatic5.arlo.res.model.Person;
import isty.iatic5.arlo.res.model.Room;
import isty.iatic5.arlo.res.model.TimeSlot;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Contrôleur pour ReservationEdit.fxml 
 * 
 * @author Clément Lefevre
 *
 */
public class ReservationCreateController {
	@FXML 
	private ComboBox<Person> reserverBox;
	
	@FXML 
	private ComboBox<Room> roomBox;
	
	@FXML
	private MenuButton timeSlotSelector;
	
	private List<CheckBox> timeSlotBoxes;
	
	private List<TimeSlot> selectedTimeSlots;
	
	private Stage editStage;
		
	private boolean okClicked;	
	
	private ResourcesInterface resourcesManager;
	
	
	Callback<ListView<Person>, ListCell<Person>> personCellFactory = new Callback<ListView<Person>, ListCell<Person>>() {
	    @Override
	    public ListCell<Person> call(ListView<Person> l) {
	        return new ListCell<Person>() {
	            @Override
	            protected void updateItem(Person person, boolean empty) {
	                super.updateItem(person, empty);
	                if (person == null || empty) {
	                    setGraphic(null);
	                } else {
	                    setText(person.getFirstName() + " " + person.getLastName());
	                }
	            }
	        };
	    }
	};
	
	Callback<ListView<Room>, ListCell<Room>> roomCellFactory = new Callback<ListView<Room>, ListCell<Room>>() {
	    @Override
	    public ListCell<Room> call(ListView<Room> l) {
	        return new ListCell<Room>() {
	            @Override
	            protected void updateItem(Room room, boolean empty) {
	                super.updateItem(room, empty);
	                if (room == null || empty) {
	                    setGraphic(null);
	                } else {
	                    setText(room.getName() + " - " + room.getBuilding() + " (" + room.getCapacity() + " places)");
	                }
	            }
	        };
	    }
	};
		
	
	@FXML
	private void initialize() {
		reserverBox.setCellFactory(personCellFactory);
		reserverBox.setButtonCell(personCellFactory.call(null));

		roomBox.setCellFactory(roomCellFactory);
		roomBox.setButtonCell(roomCellFactory.call(null));

		timeSlotBoxes = new ArrayList<CheckBox>();
		selectedTimeSlots = new ArrayList<TimeSlot>();
	}
	
	/**
	 * Clic sur le bouton OK
	 */
	@FXML 
    private void handleOk() {
		// Récupération des créneaux sélectionnés
		for(CheckBox c: timeSlotBoxes) {
			if(c.isSelected()) {
				selectedTimeSlots.add((TimeSlot) c.getUserData());
			}
		}
		
		try {
			resourcesManager.createMultipleReservations(reserverBox.getValue(), roomBox.getValue(), selectedTimeSlots);
			
			// Fermeture de la fenêtre
			okClicked = true;
			editStage.close();
		} catch (InvalidInputException e) { // Saisie des champs incorrect
			showAlert(e.getMessage());
			selectedTimeSlots.clear();
		}       
    }
	
	/**
	 * Clic sur le bouton Cancel
	 */
	@FXML 
    private void handleCancel() {
		editStage.close();
    }
	
    /**
     * Renvoie vrai si le bouton OK a été cliqué
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

	
	/** 
	 * @param editStage
	 */
	public void setEditStage(Stage editStage) {
		this.editStage = editStage;
	}
	
	/**
	 * Affiche une alerte en cas d'erreur
	 * @param errmsg
	 */
	public void showAlert(String errmsg) {
		Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(editStage);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errmsg);
        alert.showAndWait();
	}

	/**
	 * Donne une référence du resourcesManager. Permet d'initialiser le contenu de la fenêtre
	 * @param resourcesManager
	 */
	public void setResourcesManager(ResourcesInterface resourcesManager) {
		this.resourcesManager = resourcesManager;
		
		this.reserverBox.getItems().addAll(resourcesManager.getPersonData());
		this.roomBox.getItems().addAll(resourcesManager.getRoomData());
		
		for(TimeSlot t: resourcesManager.getTimeSlotData()) {
			CheckBox checkbox = new CheckBox("[" + t.getDay().toString() + "] " + t.getStart().toString() + "-" + t.getEnd().toString());
			checkbox.setUserData(t);
			CustomMenuItem cmi = new CustomMenuItem(checkbox);
			cmi.setHideOnClick(false);
			timeSlotSelector.getItems().add(cmi);	
			timeSlotBoxes.add(checkbox);
		}
	}
	
}
