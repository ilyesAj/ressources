package isty.iatic5.arlo.res.facade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import isty.iatic5.arlo.res.model.ExistingReferenceException;
import isty.iatic5.arlo.res.model.InvalidInputException;
import isty.iatic5.arlo.res.model.NullElementException;
import isty.iatic5.arlo.res.model.Person;
import isty.iatic5.arlo.res.model.Reservation;
import isty.iatic5.arlo.res.model.Room;
import isty.iatic5.arlo.res.model.Status;
import isty.iatic5.arlo.res.model.TimeSlot;
import javafx.collections.ObservableList;

/**
 * Interface exposant les fonctions de la façade
 * 
 */
public interface ResourcesInterface {

	// CRÉATION //
	/**
	 * Crée une personne à partir de ses attributs
	 * 
	 * @param personId
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param status
	 * @throws InvalidInputException
	 */
	public void createPerson(String personId, String firstName, String lastName, String email, Status status)
			throws InvalidInputException;

	/**
	 * Crée une salle à partir de ses attributs
	 * 
	 * @param roomId
	 * @param roomName
	 * @param capacity
	 * @param buildingName
	 * @throws InvalidInputException
	 */
	public void createRoom(String roomId, String roomName, Integer capacity, String buildingName)
			throws InvalidInputException;

	/**
	 * Crée un créneau à partir de ses attributs
	 * 
	 * @param timeSlotId
	 * @param day
	 * @param start
	 * @param end
	 * @throws InvalidInputException
	 */
	public void createTimeSlot(String timeSlotId, LocalDate day, LocalTime start, LocalTime end)
			throws InvalidInputException;

	/**
	 * Crée une réservation à partir de ses attributs (personne, salle, créneau)
	 * 
	 * @param person
	 * @param room
	 * @param timeSlot
	 * @throws InvalidInputException
	 */
	public void createReservation(Person person, Room room, TimeSlot timeSlot) throws InvalidInputException;

	/**
	 * Créer plusieurs réservations pour une personne, une salle et une liste de
	 * créneaux donnés
	 * 
	 * @param person
	 * @param room
	 * @param timeSlots
	 * @throws InvalidInputException
	 */
	public void createMultipleReservations(Person person, Room room, List<TimeSlot> timeSlots)
			throws InvalidInputException;

	// SUPPRESSION //
	/**
	 * Supprime une personne donnée
	 * 
	 * @param person
	 * @throws NullElementException
	 * @throws ExistingReferenceException
	 */
	public void deletePerson(Person person) throws NullElementException, ExistingReferenceException;

	/**
	 * Supprime une personne par son ID
	 * 
	 * @param personId
	 * @throws NullElementException
	 * @throws ExistingReferenceException
	 */
	public void deletePersonById(String personId) throws NullElementException, ExistingReferenceException;

	/**
	 * Supprime une salle donnée
	 * 
	 * @param room
	 * @throws NullElementException
	 * @throws ExistingReferenceException
	 */
	public void deleteRoom(Room room) throws NullElementException, ExistingReferenceException;

	/**
	 * Supprime une salle par son ID
	 * 
	 * @param roomId
	 * @throws NullElementException
	 * @throws ExistingReferenceException
	 */
	public void deleteRoomById(String roomId) throws NullElementException, ExistingReferenceException;

	/**
	 * Supprime un créneau donné
	 * 
	 * @param timeSlot
	 * @throws NullElementException
	 * @throws ExistingReferenceException
	 */
	public void deleteTimeSlot(TimeSlot timeSlot) throws NullElementException, ExistingReferenceException;

	/**
	 * Supprime un créneau par son ID
	 * 
	 * @param timeSlotId
	 * @throws NullElementException
	 * @throws ExistingReferenceException
	 */
	public void deleteTimeSlotById(String timeSlotId) throws NullElementException, ExistingReferenceException;

	/**
	 * Supprime une réservation donnée
	 * 
	 * @param reservation
	 * @throws NullElementException
	 */
	public void deleteReservation(Reservation reservation) throws NullElementException;

	/**
	 * Supprime une réservation par son ID
	 * 
	 * @param roomId
	 * @param personId
	 * @param timeSlotId
	 * @throws NullElementException
	 */
	public void deleteReservationById(String roomId, String personId, String timeSlotId) throws NullElementException;

	// ÉDITION
	/**
	 * Modifie le créneau d'une réservation donnée
	 * 
	 * @param reservation
	 * @param timeSlot
	 * @throws NullElementException
	 * @throws InvalidInputException
	 */
	public void editReservation(Reservation reservation, TimeSlot timeSlot)
			throws NullElementException, InvalidInputException;

	// LECTURE
	/**
	 * Récupère la liste des personnes du système
	 * 
	 * @return la liste des personnes
	 */
	public ObservableList<Person> getPersonData();

	/**
	 * Récupère la liste des créneaux du système
	 * 
	 * @return la liste des créneaux
	 */
	public ObservableList<TimeSlot> getTimeSlotData();

	/**
	 * Récupère la liste des salles du système
	 * 
	 * @return la liste des salles
	 */
	public ObservableList<Room> getRoomData();

	/**
	 * Récupère la liste des réservations du système
	 * 
	 * @return la liste des réservations
	 */
	public ObservableList<Reservation> getReservationData();

	/**
	 * Récupère une personne d'ID donné
	 * 
	 * @param personId
	 * @return person
	 */
	Person getPerson(String personId);

	/**
	 * Renvoie une salle d'ID donné
	 * 
	 * @param roomId
	 * @return room
	 */
	Room getRoom(String roomId);

	/**
	 * Renvoie un créneau d'ID donné
	 * 
	 * @param timeSlotId
	 * @return timeSlot
	 */
	TimeSlot getTimeSlot(String timeSlotId);

	/**
	 * Reno
	 * 
	 * @param roomId
	 * @param personId
	 * @param timeSlotId
	 * @return reservation
	 */
	Reservation getReservation(String roomId, String personId, String timeSlotId);
}
