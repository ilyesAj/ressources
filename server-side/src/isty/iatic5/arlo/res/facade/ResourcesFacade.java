package isty.iatic5.arlo.res.facade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import isty.iatic5.arlo.res.model.ExistingReferenceException;
import isty.iatic5.arlo.res.model.InvalidInputException;
import isty.iatic5.arlo.res.model.NullElementException;
import isty.iatic5.arlo.res.model.Person;
import isty.iatic5.arlo.res.model.Reservation;
import isty.iatic5.arlo.res.model.Room;
import isty.iatic5.arlo.res.model.Status;
import isty.iatic5.arlo.res.model.TimeSlot;
import isty.iatic5.arlo.res.model.Validators;
import isty.iatic5.arlo.res.util.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Classe Facade, contient les fonctionnalités de l'application
 *
 */
public class ResourcesFacade implements ResourcesInterface {

	/**
	 * Personnes
	 */
	private ObservableList<Person> personData = FXCollections.observableArrayList();

	/**
	 * Salles
	 */
	private ObservableList<Room> roomData = FXCollections.observableArrayList();

	/**
	 * Créneaux
	 */
	private ObservableList<TimeSlot> timeSlotData = FXCollections.observableArrayList();

	/**
	 * Réservations
	 */
	private ObservableList<Reservation> reservationData = FXCollections.observableArrayList();

	/**
	 * Gestionnaire de la BDD
	 */
	private DatabaseManager dataBaseManager;

	/**
	 * Constructeur
	 */
	public ResourcesFacade() {
		// Initialisation du gestionnaire de BDD
		dataBaseManager = new DatabaseManager();
		dataBaseManager.setResourcesManager(this);
		dataBaseManager.loadResources();
	}

	/****************************************************************************************/
	/***************************************
	 * CRÉATION
	 ***************************************/
	/****************************************************************************************/

	/**
	 * Créer une personne à partir de ses attributs
	 */
	@Override
	public void createPerson(String id, String firstName, String lastName, String email, Status status)
			throws InvalidInputException {
		String errmsg = Validators.isPersonValid(firstName, lastName, email, status);
		if (errmsg == "") {
			Person newPerson = new Person(id, firstName, lastName, email, status);
			this.personData.add(newPerson);
			this.dataBaseManager.saveResources();
		} else {
			throw new InvalidInputException(errmsg);
		}
	}

	/**
	 * Créer une salle à partir de ses attributs
	 */
	@Override
	public void createRoom(String id, String roomName, Integer roomCapacity, String roomBuilding)
			throws InvalidInputException {
		String errmsg = Validators.isRoomValid(roomName, roomCapacity, roomBuilding);
		if (errmsg == "") {
			Room newRoom = new Room(id, roomName, roomCapacity, roomBuilding);
			this.roomData.add(newRoom);
			dataBaseManager.saveResources();
		} else {
			throw new InvalidInputException(errmsg);
		}
	}

	/**
	 * Créer un créneau à partir de ses attributs
	 */
	@Override
	public void createTimeSlot(String id, LocalDate day, LocalTime start, LocalTime end) throws InvalidInputException {
		String errmsg = Validators.isTimeSlotValid(start, end, day);
		if (errmsg == "") {
			TimeSlot newTS = new TimeSlot(id, start, end, day);
			this.timeSlotData.add(newTS);
			dataBaseManager.saveResources();
		} else {
			throw new InvalidInputException(errmsg);
		}
	}

	/**
	 * Créer une réservation à partir de ses attributs
	 */
	@Override
	public void createReservation(Person person, Room room, TimeSlot timeSlot) throws InvalidInputException {
		String errmsg = Validators.isReservationValid(person, room, timeSlot, reservationData);
		if (errmsg == "") {
			Reservation newRes = new Reservation(timeSlot, person, room);
			this.reservationData.add(newRes);
			dataBaseManager.saveResources();
		} else {
			throw new InvalidInputException(errmsg);
		}
	}

	/**
	 * Créer des réservations pour un ensemble de créneaux
	 */
	@Override
	public void createMultipleReservations(Person person, Room room, List<TimeSlot> timeSlots)
			throws InvalidInputException {
		if (timeSlots == null || timeSlots.size() == 0) {
			throw new InvalidInputException("Invalid time slots");
		}

		for (TimeSlot timeSlot : timeSlots) {
			String errmsg = Validators.isReservationValid(person, room, timeSlot, reservationData);
			if (errmsg == "") {
				Reservation newRes = new Reservation(timeSlot, person, room);
				reservationData.add(newRes);
				dataBaseManager.saveResources();
			} else {
				throw new InvalidInputException(errmsg);
			}
		}
	}

	/****************************************************************************************/
	/*************************************
	 * SUPPRESSION
	 **************************************/
	/****************************************************************************************/

	/**
	 * Supprime une personne
	 */
	@Override
	public void deletePerson(Person person) throws NullElementException, ExistingReferenceException {
		if (person != null) {
			if (Validators.isPersonDeleteSafe(person, reservationData)) {
				personData.remove(person);
				dataBaseManager.saveResources();
			} else {
				throw new ExistingReferenceException(person.getClass().getSimpleName(), person.getId());
			}
		} else {
			throw new NullElementException("No person to delete");
		}
	}

	/**
	 * Supprime une personne par son ID
	 */
	@Override
	public void deletePersonById(String personId) throws NullElementException, ExistingReferenceException {
		deletePerson(getPerson(personId));
	}

	/**
	 * Supprime une salle
	 */
	@Override
	public void deleteRoom(Room room) throws NullElementException, ExistingReferenceException {
		if (room != null) {
			if (Validators.isRoomDeleteSafe(room, reservationData)) {
				roomData.remove(room);
				dataBaseManager.saveResources();
			} else {
				throw new ExistingReferenceException(room.getClass().getSimpleName(), room.getId());
			}
		} else {
			throw new NullElementException("No room to delete");
		}
	}

	/**
	 * Supprime une salle par son ID
	 */
	@Override
	public void deleteRoomById(String roomId) throws NullElementException, ExistingReferenceException {
		deleteRoom(getRoom(roomId));
	}

	/**
	 * Supprime un créneau
	 */
	@Override
	public void deleteTimeSlot(TimeSlot timeSlot) throws NullElementException, ExistingReferenceException {
		if (timeSlot != null) {
			if (Validators.isTSDeleteSafe(timeSlot, reservationData)) {
				timeSlotData.remove(timeSlot);
				dataBaseManager.saveResources();
			} else {
				throw new ExistingReferenceException(timeSlot.getClass().getSimpleName(), timeSlot.getId());
			}
		} else {
			throw new NullElementException("No timeslot to delete");
		}
	}

	/**
	 * Supprime un créneau par son ID
	 */
	@Override
	public void deleteTimeSlotById(String timeSlotId) throws NullElementException, ExistingReferenceException {
		deleteTimeSlot(getTimeSlot(timeSlotId));
	}

	/**
	 * Supprime une réservation
	 */
	@Override
	public void deleteReservation(Reservation reservation) throws NullElementException {
		if (reservation != null) {
			reservationData.remove(reservation);
			dataBaseManager.saveResources();
		} else {
			throw new NullElementException("No reservation to delete");
		}
	}

	/**
	 * Supprime une réservation par son ID
	 */
	@Override
	public void deleteReservationById(String roomId, String personId, String timeSlotId) throws NullElementException {
		deleteReservation(getReservation(roomId, personId, timeSlotId));
	}

	/****************************************************************************************/
	/***************************************
	 * ÉDITION *********************************£
	 ******/
	/****************************************************************************************/

	/**
	 * Changement de créneau d'une réservation
	 */
	@Override
	public void editReservation(Reservation toEditReservation, TimeSlot timeSlot)
			throws NullElementException, InvalidInputException {
		if (toEditReservation != null) {
			// Création d'une liste temporaire sans la réservation à éditer
			List<Reservation> toEditReservationData = new ArrayList<Reservation>();
			toEditReservationData.addAll(reservationData);
			toEditReservationData.remove(toEditReservation);
			String errmsg = Validators.isReservationValid(toEditReservation.getPerson(), toEditReservation.getRoom(),
					timeSlot, toEditReservationData);
			if (errmsg == "") {
				toEditReservation.setTimeSlot(timeSlot);
				dataBaseManager.saveResources();
			} else {
				throw new InvalidInputException(errmsg);
			}
		} else {
			throw new NullElementException("No reservation to edit");
		}
	}

	/****************************************************************************************/
	/***************************************
	 * LECTURE
	 ****************************************/
	/****************************************************************************************/

	/**
	 * Renvoie la liste des personnes
	 */
	public ObservableList<Person> getPersonData() {
		return personData;
	}

	/**
	 * Renvoie la liste des salles
	 */
	public ObservableList<Room> getRoomData() {
		return roomData;
	}

	/**
	 * Renvoie la liste des créneaux
	 */
	public ObservableList<TimeSlot> getTimeSlotData() {
		return timeSlotData;
	}

	/**
	 * Renvoie la liste des réservations
	 */
	public ObservableList<Reservation> getReservationData() {
		return reservationData;
	}

	/**
	 * Récupère une personne par son ID
	 * 
	 * @param personId
	 * @return
	 */
	@Override
	public Person getPerson(String personId) {
		for (Person p : personData) {
			if (p.getId() == personId) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Récupère une salle par son ID
	 */
	@Override
	public Room getRoom(String roomId) {
		for (Room r : roomData) {
			if (r.getId() == roomId) {
				return r;
			}
		}
		return null;
	}

	/**
	 * Récupère une créneau par son ID
	 */
	@Override
	public TimeSlot getTimeSlot(String timeSlotId) {
		for (TimeSlot t : timeSlotData) {
			if (t.getId() == timeSlotId) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Récupère une réservation par son ID
	 */
	@Override
	public Reservation getReservation(String roomId, String personId, String timeSlotId) {
		Reservation reservation = null;
		for (Reservation r : reservationData) {
			if (r.getRoom().getId() == roomId && r.getPerson().getId() == personId
					&& r.getTimeSlot().getId() == timeSlotId) {
				reservation = r;
				break;
			}
		}
		return reservation;
	}
}
