package isty.iatic5.arlo.res.view;

import isty.iatic5.arlo.res.facade.ResourcesInterface;
import isty.iatic5.arlo.res.model.InvalidInputException;
import isty.iatic5.arlo.res.model.NullElementException;
import isty.iatic5.arlo.res.model.Person;
import isty.iatic5.arlo.res.model.Reservation;
import isty.iatic5.arlo.res.model.Room;
import isty.iatic5.arlo.res.model.TimeSlot;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Contrôleur pour ReservationEdit.fxml
 * 
 * @author Clément Lefevre
 *
 */
public class ReservationEditController {

	@FXML 
	private ComboBox<Person> reserverBox;

	@FXML 
	private ComboBox<Room> roomBox;

	@FXML
	private ComboBox<TimeSlot> timeSlotBox;

	private Reservation toEditReservation;

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

	Callback<ListView<TimeSlot>, ListCell<TimeSlot>> timeSlotCellFactory = new Callback<ListView<TimeSlot>, ListCell<TimeSlot>>() {
		@Override
		public ListCell<TimeSlot> call(ListView<TimeSlot> l) {
			return new ListCell<TimeSlot>() {
				@Override
				protected void updateItem(TimeSlot timeSlot, boolean empty) {
					super.updateItem(timeSlot, empty);
					if (timeSlot == null || empty) {
						setGraphic(null);
					} else {
						setText("[" + timeSlot.getDay().toString() + "]  " + timeSlot.getStart() + " - " + timeSlot.getEnd());
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

		timeSlotBox.setCellFactory(timeSlotCellFactory);
		timeSlotBox.setButtonCell(timeSlotCellFactory.call(null));
	}


	/**
	 * Clic sur le bouton OK
	 */
	@FXML 
	private void handleOk() {
		try {
			resourcesManager.editReservation(toEditReservation, timeSlotBox.getValue());

			// Fermeture de la fenêtre
			okClicked = true;
			editStage.close();
		} catch (InvalidInputException e) { // Saisie des champs incorrect
			showAlert(e.getMessage());
		} catch (NullElementException e) {
			showAlert(e.getMessage());
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
	 * 
	 * @param resourcesManager
	 */
	public void setResourcesManager(ResourcesInterface resourcesManager) {
		this.resourcesManager = resourcesManager;

		this.reserverBox.getItems().addAll(resourcesManager.getPersonData());
		this.roomBox.getItems().addAll(resourcesManager.getRoomData());
		this.timeSlotBox.getItems().addAll(resourcesManager.getTimeSlotData());
	}


	/**
	 * Passe la réservation à éditer
	 * @throws NullElementException 
	 */
	public void setReservation(Reservation toEditReservation) throws NullElementException {
		if(toEditReservation != null) {
			this.toEditReservation = toEditReservation;

			// Fixe les valeurs de la salle et de la personne
			this.roomBox.setValue(toEditReservation.getRoom());
			this.roomBox.setDisable(true);
			this.roomBox.setStyle("-fx-opacity: 1");
			
			this.reserverBox.setValue(toEditReservation.getPerson());
			this.reserverBox.setDisable(true);
			this.reserverBox.setStyle("-fx-opacity: 1");
		} else {
			throw new NullElementException("No reservation to edit");
		}
	}
}
