package isty.iatic5.arlo.res.view;

import java.time.LocalDate;
import java.time.LocalTime;

import isty.iatic5.arlo.res.facade.ResourcesInterface;
import isty.iatic5.arlo.res.model.ExistingReferenceException;
import isty.iatic5.arlo.res.model.NullElementException;
import isty.iatic5.arlo.res.model.Person;
import isty.iatic5.arlo.res.model.Reservation;
import isty.iatic5.arlo.res.model.Room;
import isty.iatic5.arlo.res.model.TimeSlot;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controleur pour ResourcesOverview.fxml
 * 
 * (Fenetre d'affichage des ressources)
 * 
 * @author Clement Lefevre
 *
 */
public class ResourcesOverviewController {
	// Tableau des personnes
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, String> lastNameColumn;

	// Détails des personnes
	@FXML
	private Label personIdLabel;
	@FXML 
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label statusLabel;

	// Tableau des salles
	@FXML 
	private TableView<Room> roomTable;
	@FXML 
	private TableColumn<Room, String> roomNameColumn;

	// Détails des salles
	@FXML
	private Label roomIdLabel;
	@FXML
	private Label roomNameLabel;
	@FXML 
	private Label roomCapacityLabel;
	@FXML
	private Label roomBuildingLabel;

	// Table des creneaux
	@FXML 
	private TableView<TimeSlot> timeSlotTable;
	@FXML
	private TableColumn<TimeSlot, LocalTime> startColumn;
	@FXML
	private TableColumn<TimeSlot, LocalTime> endColumn;
	@FXML 
	private TableColumn<TimeSlot, LocalDate> dayColumn;

	// Détails des creneaux
	@FXML
	private Label timeSlotIdLabel;
	@FXML
	private Label startLabel;
	@FXML 
	private Label endLabel;
	@FXML 
	private Label dayLabel;

	// Tableau des reservations
	@FXML 
	private TableView<Reservation> reservationTable;
	@FXML
	private TableColumn<Reservation, String> reserverColumn;
	@FXML
	private TableColumn<Reservation, String> roomColumn;
	@FXML
	private TableColumn<Reservation, String> timeSlotColumn;

	// Détails des réservations
	@FXML
	private Label resRoomIdLabel;
	@FXML
	private Label resRoomNameLabel;
	@FXML
	private Label resRoomBuildingLabel;
	@FXML
	private Label resRoomCapacityLabel;
	@FXML
	private Label resTimeSlotId;
	@FXML
	private Label resDayLabel;
	@FXML
	private Label resTimesLabel;
	@FXML
	private Label reserverIdLabel;
	@FXML
	private Label reserverNameLabel;
	@FXML 
	private Label reserverStatusLabel;
	@FXML
	private Label reserverEmailLabel;

	/**
	 * Référence à l'IHM principale
	 */
	private MainGUI mainGUI;

	/**
	 * Référence à l'interface exposant les fonctionnalités
	 */
	private ResourcesInterface resourcesManager;


	/**
	 * Constructeur
	 */
	public ResourcesOverviewController() {

	}

	/**
	 * Initialise le contrôleur 
	 */
	@FXML
	private void initialize() {
		// Init the person table with the columns
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

		// Init the room table with the column
		roomNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		// Init the TS table with the columns
		startColumn.setCellValueFactory(cellData -> cellData.getValue().startProperty());
		endColumn.setCellValueFactory(cellData -> cellData.getValue().endProperty());
		dayColumn.setCellValueFactory(cellData -> cellData.getValue().dayProperty());

		// Init the reservation table with the columns 
		roomColumn.setCellValueFactory(cellData -> Bindings.concat(
				cellData.getValue().getRoom().nameProperty(),
				" - ",
				cellData.getValue().getRoom().buildingProperty()));

		timeSlotColumn.setCellValueFactory(cellData -> Bindings.concat(
				cellData.getValue().getTimeSlot().dayProperty(),
				" - ",
				cellData.getValue().getTimeSlot().startProperty(),
				"-",
				cellData.getValue().getTimeSlot().endProperty()));

		reserverColumn.setCellValueFactory(cellData -> Bindings.concat(
				cellData.getValue().getPerson().firstNameProperty(),
				" ",
				cellData.getValue().getPerson().lastNameProperty()));

		// Clear person, room, TS & reservation details
		showPersonDetails(null);
		showRoomDetails(null);
		showTSDetails(null);
		showReservationDetails(null);

		// Add events listeners 
		personTable.getSelectionModel().selectedItemProperty().addListener((obsPerson, oldPerson, newPerson) -> showPersonDetails(newPerson));
		roomTable.getSelectionModel().selectedItemProperty().addListener((obsRoom, oldRoom, newRoom) -> showRoomDetails(newRoom));
		timeSlotTable.getSelectionModel().selectedItemProperty().addListener((obsTimeSlot, oldTimeSlot, newTimeSlot) -> showTSDetails(newTimeSlot));
		reservationTable.getSelectionModel().selectedItemProperty().addListener((obsRes, oldRes, newRes) -> showReservationDetails(newRes));
	}

	/**
	 * Affectation de l'interface
	 */
	public void setResourcesManager(ResourcesInterface resourcesManager) {
		this.resourcesManager = resourcesManager;

		// Remplissage des tableaux
		personTable.setItems(resourcesManager.getPersonData());
		roomTable.setItems(resourcesManager.getRoomData());
		timeSlotTable.setItems(resourcesManager.getTimeSlotData());
		reservationTable.setItems(resourcesManager.getReservationData());
	}

	/**
	 * Référence à l'IHM principale
	 * @param mainGUI
	 */
	public void setMainGUI(MainGUI mainGUI) {
		this.mainGUI = mainGUI;
	}

	/**
	 * Affiche les détails d'une personne sélectionnée
	 * @param person
	 */
	private void showPersonDetails(Person person) {
		if(person != null) {
			// Remplis les champs avec les données de la personne
			personIdLabel.setText(person.getId());
			firstNameLabel.setText(person.getFirstName());
			lastNameLabel.setText(person.getLastName());
			emailLabel.setText(person.getEmail());
			statusLabel.setText(person.getStatus().toString());
		} else {
			// Aucune personne sélectionnée : champs vides
			personIdLabel.setText("");
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			emailLabel.setText("");
			statusLabel.setText("");			
		}
	}

	/**
	 * Affiche les détails d'une salle sélectionnée
	 * @param room
	 */
	private void showRoomDetails(Room room) {
		if(room != null) {
			roomIdLabel.setText(room.getId());
			roomNameLabel.setText(room.getName());
			roomCapacityLabel.setText(Integer.toString(room.getCapacity()));
			roomBuildingLabel.setText(room.getBuilding());
		} else {
			roomIdLabel.setText("");
			roomNameLabel.setText("");
			roomCapacityLabel.setText("");
			roomBuildingLabel.setText("");
		}
	}

	/**
	 * Affiche les détails d'un créneau sélectionné
	 * @param timeSlot
	 */	
	private void showTSDetails(TimeSlot timeSlot) {
		if(timeSlot != null) {
			timeSlotIdLabel.setText(timeSlot.getId());
			startLabel.setText(timeSlot.getStart().toString());
			endLabel.setText(timeSlot.getEnd().toString());
			dayLabel.setText(timeSlot.getDay().toString());
		} else {
			timeSlotIdLabel.setText("");
			startLabel.setText("");
			endLabel.setText("");
			dayLabel.setText("");
		}
	}

	/**
	 * Affiche les détails d'une réservation sélectionnée
	 * @param reservation
	 */
	private void showReservationDetails(Reservation reservation) {
		if(reservation != null) {
			resRoomIdLabel.setText(reservation.getRoom().getId());
			resRoomNameLabel.setText(reservation.getRoom().getName());
			resRoomBuildingLabel.setText(reservation.getRoom().getBuilding());
			resRoomCapacityLabel.setText(reservation.getRoom().getCapacity().toString());
			resTimeSlotId.setText(reservation.getTimeSlot().getId());
			resDayLabel.setText(reservation.getTimeSlot().getDay().toString());
			resTimesLabel.setText(reservation.getTimeSlot().getStart().toString() + "-" + reservation.getTimeSlot().getEnd().toString());
			reserverIdLabel.setText(reservation.getPerson().getId());
			reserverNameLabel.setText(reservation.getPerson().getFirstName() + " " + reservation.getPerson().getLastName());
			reserverStatusLabel.setText(reservation.getPerson().getStatus().toString());
			reserverEmailLabel.setText(reservation.getPerson().getEmail());
		} else {
			resRoomIdLabel.setText("");
			resRoomNameLabel.setText("");
			resRoomBuildingLabel.setText("");
			resRoomCapacityLabel.setText("");
			resTimeSlotId.setText("");
			resDayLabel.setText("");
			resTimesLabel.setText("");
			reserverIdLabel.setText("");
			reserverNameLabel.setText("");
			reserverStatusLabel.setText("");
			reserverEmailLabel.setText("");
		}
	}

	/**
	 * Clic sur le bouton Add (personne)
	 */
	@FXML
	private void handleCreatePerson() {		
		boolean okClicked = mainGUI.showPersonCreate();

		if(okClicked) {
			personTable.refresh();
		}
	}

	/**
	 * Clic sur le bouton Add (salle)
	 */
	@FXML
	private void handleCreateRoom() {		
		boolean okClicked = mainGUI.showRoomCreate();

		if(okClicked) {
			roomTable.refresh();
		}
	}

	/**
	 * Clic sur le bouton Add (créneau)
	 */
	@FXML
	private void handleCreateTS() {		
		boolean okClicked = mainGUI.showTimeSlotCreate();

		if(okClicked) {
			timeSlotTable.refresh();
		}
	}

	/**
	 * Clic sur le bouton Add (reservation)
	 */
	@FXML
	private void handleCreateReservation() {				
		boolean okClicked = mainGUI.showReservationCreate();

		if(okClicked) {
			timeSlotTable.refresh();
		}
	}

	/**
	 * Clic sur le bouton Delete (personne)
	 */
	@FXML
	private void handleDeletePerson() {
		try {
			resourcesManager.deletePerson(personTable.getSelectionModel().getSelectedItem());
			personTable.refresh();
		} catch (NullElementException e) { // Suppression impossible, aucun élément sélectionné
			showNothingSelectedAlert(e.getMessage());	
		} catch (ExistingReferenceException e) { // Suppression impossible, une référence existe
			showUnsafeDeleteAlert(e.getMessage());
		}
	}

	/**
	 * Clic sur le bouton Delete (salle)
	 */
	@FXML
	private void handleDeleteRoom() {
		try {
			resourcesManager.deleteRoom(roomTable.getSelectionModel().getSelectedItem());
			roomTable.refresh();
		} catch (NullElementException e) {
			showNothingSelectedAlert(e.getMessage());
		}
		catch (ExistingReferenceException e) {
			showUnsafeDeleteAlert(e.getMessage());
		}
	}

	/**
	 * Clic sur le bouton Delete (créneau)
	 */
	@FXML
	private void handleDeleteTS() {
		try {
			resourcesManager.deleteTimeSlot(timeSlotTable.getSelectionModel().getSelectedItem());
			timeSlotTable.refresh();
		} catch (NullElementException e) {
			showNothingSelectedAlert(e.getMessage());
		}
		catch (ExistingReferenceException e) {
			showUnsafeDeleteAlert(e.getMessage());
		}
	}

	/**
	 * Clic sur le bouton Delete (reservation)
	 */
	@FXML
	private void handleDeleteReservation() {
		try {
			resourcesManager.deleteReservation(reservationTable.getSelectionModel().getSelectedItem());
		} catch (NullElementException e) {
			showNothingSelectedAlert(e.getMessage());
		}
	}

	/**
	 * Clic sur le bouton Edit (reservation)
	 */
	@FXML
	private void handleEditReservation() {
		try {
			Reservation toEditReservation = reservationTable.getSelectionModel().getSelectedItem();
			boolean okClicked = mainGUI.showReservationEdit(toEditReservation);

			if(okClicked) {
				reservationTable.refresh();
				showReservationDetails(toEditReservation);
			}
		} catch(NullElementException e) {
			showNothingSelectedAlert(e.getMessage());
		}
	}


	/**
	 * Clic sur le bouton Refresh
	 */
	@FXML
	private void handleRefresh() {
		personTable.refresh();
		roomTable.refresh();
		timeSlotTable.refresh();
		reservationTable.refresh();		
	}

	/**
	 * Afiche une alerte liée à une suppression invalide (référence)
	 * @param errmsg
	 */
	private void showUnsafeDeleteAlert(String errmsg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Unsafe delete");
		alert.setHeaderText("Existing reference prevents deletion");
		alert.setContentText(errmsg);
		alert.showAndWait();
	}


	/**
	 * Affiche une alerte liée à une non séléction d'élément
	 * @param errmsg
	 */
	private void showNothingSelectedAlert(String errmsg) {
		Alert alert = new Alert(AlertType.WARNING);	// Crée une alerte
		alert.setTitle("No Selection");
		alert.setHeaderText("Nothing selected");
		alert.setContentText(errmsg);
		alert.showAndWait();
	}


}
