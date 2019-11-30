package isty.iatic5.arlo.res.facade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import communicationProtocol.RestClient;
import isty.iatic5.arlo.res.model.ExistingReferenceException;
import isty.iatic5.arlo.res.model.InvalidInputException;
import isty.iatic5.arlo.res.model.NullElementException;
import isty.iatic5.arlo.res.model.Person;
import isty.iatic5.arlo.res.model.Reservation;
import isty.iatic5.arlo.res.model.Room;
import isty.iatic5.arlo.res.model.Status;
import isty.iatic5.arlo.res.model.TimeSlot;
import isty.iatic5.arlo.res.model.Validators;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Classe Facade, contient les fonctionnalités de l'application
 *
 */
public class ResourcesFacade implements ResourcesInterface{

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
	
	private RestClient restCli;

	/**
	 * Gestionnaire de la BDD
	 */

	/**
	 * Constructeur
	 */
	public ResourcesFacade() {
		// Initialisation du gestionnaire de BDD
		restCli = new RestClient("0.0.0.0","4567");
		
	}

	/****************************************************************************************/
	/*************************************** CRÉATION ***************************************/
	/****************************************************************************************/

	/**
	 * Créer une personne à partir de ses attributs
	 */
	@Override
	public void createPerson(String id, String firstName, String lastName, String email, Status status) throws InvalidInputException {
		String errmsg = Validators.isPersonValid(firstName, lastName, email, status);
		if(errmsg == "") {
			Person newPerson = new Person(id, firstName, lastName, email, status);
			String res = restCli.postCreatePerson(id,firstName,lastName,email, status.name());
			JSONObject obj = new JSONObject(res);
			if (obj.getString("result").equals("ok"))
			{
				this.personData.add(newPerson);
			}
			else 
			{
				throw new InvalidInputException(res);
			}
		} else {
			throw new InvalidInputException(errmsg);
		}
	}

	/**
	 * Créer une salle à partir de ses attributs
	 */
	@Override
	public void createRoom(String id, String roomName, Integer roomCapacity, String roomBuilding) throws InvalidInputException {
		String errmsg = Validators.isRoomValid(roomName, roomCapacity, roomBuilding);
		String res = restCli.postCreateRoom(id, roomName, roomCapacity.toString(), roomBuilding);
		if(errmsg == "") {
			Room newRoom = new Room(id, roomName, roomCapacity, roomBuilding);
			JSONObject obj = new JSONObject(res);
			if (obj.getString("result").equals("ok"))
			{
				this.roomData.add(newRoom);
			}
			else 
			{
				throw new InvalidInputException(res);
			}
		} else {
			throw new InvalidInputException(errmsg);
		}
	}

	/**
	 * Créer un créneau à partir de ses attributs
	 */
	@Override
	public void createTimeSlot(String id, LocalDate day, LocalTime start, LocalTime end) throws InvalidInputException{
		String errmsg = Validators.isTimeSlotValid(start, end, day);
		if(errmsg == "") {
			TimeSlot newTS = new TimeSlot(id, start, end, day);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String j = day.format(formatter);
			String res = restCli.postCreateTimeSlot(id, j, start.toString(), end.toString());
			JSONObject obj = new JSONObject(res);
			if (obj.getString("result").equals("ok"))
			{
				this.timeSlotData.add(newTS);			}
			else 
			{
				throw new InvalidInputException(res);
			}
			
		} else {
			throw new InvalidInputException(errmsg);
		}
	}

	/**
	 * Créer une réservation à partir de ses attributs
	 */
	@Override
	public void createReservation(Person person, Room room, TimeSlot timeSlot) throws InvalidInputException{
		String errmsg = Validators.isReservationValid(person, room, timeSlot, reservationData);
		if(errmsg == "") {
			Reservation newRes = new Reservation(timeSlot, person, room);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String j = timeSlot.getDay().format(formatter);
			String res = restCli.postCreateReservation(person.getId(), person.getFirstName(), person.getLastName(), person.getEmail(), person.getStatus().name(), room.getId(),room.getName(), room.getCapacity().toString(),
					room.getBuilding(), timeSlot.getId(), j, timeSlot.getStart().toString(), timeSlot.getEnd().toString());
			JSONObject obj = new JSONObject(res);
			if (obj.getString("result").equals("ok"))
			{
				this.reservationData.add(newRes);		}
			else 
			{
				throw new InvalidInputException(res);
			}
		} else {
			throw new InvalidInputException(errmsg);
		}
	}

	/**
	 * Créer des réservations pour un ensemble de créneaux
	 */
	@Override
	public void createMultipleReservations(Person person, Room room, List<TimeSlot> timeSlots) throws InvalidInputException {
		if(timeSlots == null || timeSlots.size() == 0) {
			throw new InvalidInputException("Invalid time slots");
		}

		for(TimeSlot timeSlot: timeSlots) {
			String errmsg = Validators.isReservationValid(person, room, timeSlot, reservationData);
			if(errmsg == "") {
				this.createReservation( person,  room, timeSlot);
			} else {
				throw new InvalidInputException(errmsg);
			}
		}
	}


	/****************************************************************************************/
	/************************************* SUPPRESSION **************************************/
	/****************************************************************************************/

	/**
	 * Supprime une personne 
	 */
	@Override
	public void deletePerson(Person person) throws NullElementException, ExistingReferenceException {
		if(person != null) {
			if(Validators.isPersonDeleteSafe(person, reservationData)) {
				String res = restCli.postDeletePerson(person.getId(),person.getFirstName(),person.getLastName(),person.getEmail(), person.getStatus().name());
				JSONObject obj = new JSONObject(res);
				if (obj.getString("result").equals("ok"))
				{
					personData.remove(person);
				}
				else 
				{
					throw new ExistingReferenceException(res, res);
				}
				
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
		if(room != null) {
			if (Validators.isRoomDeleteSafe(room, reservationData)) {
				String res = restCli.postDeleteRoom(room.getId(), room.getName(), room.getCapacity().toString(), room.getBuilding());
				JSONObject obj = new JSONObject(res);
				if (obj.getString("result").equals("ok"))
				{
					roomData.remove(room);
				}
				else 
				{
					throw new ExistingReferenceException(res, res);
				}
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
		if(timeSlot != null) {
			if(Validators.isTSDeleteSafe(timeSlot, reservationData)) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String j = timeSlot.getDay().format(formatter);
				String res = restCli.postDeleteTimeSlot(timeSlot.getId(), j, timeSlot.getStart().toString(), timeSlot.getEnd().toString());
				JSONObject obj = new JSONObject(res);
				if (obj.getString("result").equals("ok"))
				{
					timeSlotData.remove(timeSlot);
				}
				else 
				{
					throw new ExistingReferenceException(res, res);
				}
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
		if(reservation != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String j = reservation.getTimeSlot().getDay().format(formatter);
			String res = restCli.postDeleteReservation(reservation.getPerson().getId(), reservation.getPerson().getFirstName(), reservation.getPerson().getLastName(), reservation.getPerson().getEmail(), reservation.getPerson().getStatus().name(), reservation.getRoom().getId(),reservation.getRoom().getName(), reservation.getRoom().getCapacity().toString(),
					reservation.getRoom().getBuilding(), reservation.getTimeSlot().getId(), j, reservation.getTimeSlot().getStart().toString(), reservation.getTimeSlot().getEnd().toString());
			JSONObject obj = new JSONObject(res);
			if (obj.getString("result").equals("ok"))
			{
				reservationData.remove(reservation);
			}
			else 
			{
				throw new NullElementException(res);
			}
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
	/*************************************** ÉDITION *********************************£******/
	/****************************************************************************************/	

	/**
	 * Changement de créneau d'une réservation
	 */
	@Override
	public void editReservation(Reservation toEditReservation, TimeSlot timeSlot) throws NullElementException, InvalidInputException{
		if(toEditReservation != null) {
			// Création d'une liste temporaire sans la réservation à éditer
			List<Reservation> toEditReservationData = new ArrayList<Reservation>();
			toEditReservationData.addAll(reservationData);
			toEditReservationData.remove(toEditReservation);
			String errmsg = Validators.isReservationValid(toEditReservation.getPerson(), toEditReservation.getRoom(), timeSlot, toEditReservationData);
			if(errmsg == "") {
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String j = timeSlot.getDay().format(formatter);
				String j2 = toEditReservation.getTimeSlot().getDay().format(formatter);

				String res = restCli.postEditReservation(toEditReservation.getPerson().getId(), toEditReservation.getPerson().getFirstName(), toEditReservation.getPerson().getLastName(), toEditReservation.getPerson().getEmail(), toEditReservation.getPerson().getStatus().name(), toEditReservation.getRoom().getId(),toEditReservation.getRoom().getName(), toEditReservation.getRoom().getCapacity().toString(),
						toEditReservation.getRoom().getBuilding(), toEditReservation.getTimeSlot().getId(), j2, toEditReservation.getTimeSlot().getStart().toString(), toEditReservation.getTimeSlot().getEnd().toString(), timeSlot.getId(), j, timeSlot.getStart().toString(), timeSlot.getEnd().toString());
				JSONObject obj = new JSONObject(res);
				if (obj.getString("result").equals("ok"))
				{
					
					toEditReservation.setTimeSlot(timeSlot);
					
					
				}
				else 
				{
					throw new InvalidInputException(res);
				}
				
			} else {
				throw new InvalidInputException(errmsg);
			}
		} else {
			throw new NullElementException("No reservation to edit"); 
		}
	}


	/****************************************************************************************/
	/*************************************** LECTURE ****************************************/
	/****************************************************************************************/

	/**
	 * Renvoie la liste des personnes
	 * @throws InvalidInputException 
	 */
	public ObservableList<Person> getPersonData()  {
		personData.clear();
		String res = restCli.getData("getPersonData");
		if (!res.equals("[]"))
		{
			JSONArray jsonarray = new JSONArray(res);
			for (int i = 0; i < jsonarray.length(); i++) {
			    JSONObject jsonobject = jsonarray.getJSONObject(i);
			    String personId = jsonobject.getString("personId");
			    String firstName = jsonobject.getString("firstName");
			    String lastName = jsonobject.getString("lastName");
			    String email = jsonobject.getString("email");
			    String status = jsonobject.getString("status");
			    Status stat = Status.valueOf(status);
				Person NewPerson = new Person(personId,firstName,lastName,email,stat);
				personData.add(NewPerson);
			}
		}	
		return personData;
	}

	/**
	 * Renvoie la liste des salles
	 */
	public ObservableList<Room> getRoomData() {
		roomData.clear();
		String res = restCli.getData("getRoomData");
		if (!res.equals("[]"))
		{
			JSONArray Input = new JSONArray(res);
			for(int i=0; i<Input.length(); i++) {
			    JSONObject jobject = Input.getJSONObject(i);
			    String id=jobject.getString("roomId");
				String roomName=jobject.getString("roomName");;
				Integer roomCapacity=Integer.parseInt(jobject.getString("capacity"));
				String roomBuilding=jobject.getString("buildingName");;
				Room newRoom = new Room(id, roomName, roomCapacity, roomBuilding);
				roomData.add(newRoom);
			}
		}
		
		return roomData;
	}

	/**
	 * Renvoie la liste des crÃ©neaux
	 */
	public ObservableList<TimeSlot> getTimeSlotData() {
		timeSlotData.clear();
		String res = restCli.getData("getTimeSlotData");
		if (!res.equals("[]"))
		{
			JSONArray Input = new JSONArray(res);
			for(int i=0; i<Input.length(); i++) {
			    JSONObject jobject = Input.getJSONObject(i);
			    String timeSlotId=jobject.getString("timeSlotId");
			    LocalDate day=LocalDate.parse(jobject.getString("day"));
			    LocalTime start=LocalTime.parse(jobject.getString("start"));
			    LocalTime end=LocalTime.parse(jobject.getString("end"));
			    
			    TimeSlot newTimeSlot = new TimeSlot(timeSlotId, start, end,day);
			    timeSlotData.add(newTimeSlot);
			}
		}
		
		return timeSlotData;
	}

	/**
	 * Renvoie la liste des rÃ©servations
	 */
	public ObservableList<Reservation> getReservationData() {
		reservationData.clear();
		String res = restCli.getData("getReservationData");
		if (!res.equals("[]"))
		{
			JSONArray Input = new JSONArray(res);
			for(int i=0; i<Input.length(); i++) {
			    JSONObject jobject = Input.getJSONObject(i);
			    //Timeslot
			    String timeSlotId=jobject.getString("timeSlotId");
			    LocalDate day=LocalDate.parse(jobject.getString("day"));
			    LocalTime start=LocalTime.parse(jobject.getString("start"));
			    LocalTime end=LocalTime.parse(jobject.getString("end"));
			    TimeSlot newTimeSlot = new TimeSlot(timeSlotId, start, end,day);
			    //Room
			    String id=jobject.getString("roomId");
				String roomName=jobject.getString("roomName");;
				Integer roomCapacity=Integer.parseInt(jobject.getString("capacity"));
				String roomBuilding=jobject.getString("buildingName");;
				Room newRoom = new Room(id, roomName, roomCapacity, roomBuilding);
				//Person
			    String personId = jobject.getString("personId");
			    String firstName = jobject.getString("firstName");
			    String lastName = jobject.getString("lastName");
			    String email = jobject.getString("email");
			    String status = jobject.getString("status");
			    Status stat = Status.valueOf(status);
				Person NewPerson = new Person(personId,firstName,lastName,email,stat);
				//all
				Reservation newReservation=new Reservation(newTimeSlot, NewPerson, newRoom);
				reservationData.add(newReservation);
			}
		}
		return reservationData;
	}

	/**
	 * Récupère une personne par son ID
	 * @param personId
	 * @return
	 */
	@Override
	public Person getPerson(String personId) {
		for(Person p: personData) {
			if(p.getId() == personId) {
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
		for(Room r: roomData) {
			if(r.getId() == roomId) {
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
		for(TimeSlot t: timeSlotData) {
			if(t.getId() == timeSlotId) {
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
		for(Reservation r: reservationData) {
			if(r.getRoom().getId() == roomId && r.getPerson().getId() == personId && r.getTimeSlot().getId() == timeSlotId) {
				reservation = r;
				break;
			}
		}
		return reservation;
	}
}
