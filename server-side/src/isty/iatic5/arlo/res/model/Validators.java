package isty.iatic5.arlo.res.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe exposant les méthodes de validation de création/suppression/edition de ressources
 * @author Clément Lefevre
 *
 */
public class Validators {

	// Constructeur privé pour annhiler l'instanciation
	private Validators() {
		throw new UnsupportedOperationException();
	}
	
	
	/**
	 * Vérifie la validité d'une personne
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param status
	 * @return
	 */
	public static String isPersonValid(String firstName, String lastName, String email, Status status) {
		String errmsg = "";
		if (firstName == null || firstName.length() == 0) {
			errmsg += "Invalid First Name\n";
		}
		if (lastName == null || lastName.length() == 0) {
			errmsg += "Invalid Last Name\n";
		}
		if (email == null || !isValidEmailAddress(email)) {
			errmsg += "Invalid Email\n";
		}
		if (status == null) {
			errmsg += "Invalid Status\n";
		}

		return errmsg;
	}


	/**
	 * Vérifie la validité du champ email
	 * @param email
	 * @return
	 */
	public static boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern p = Pattern.compile(ePattern);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * Vérifie la validité d'une salle
	 * @param roomName
	 * @param roomCapacity
	 * @param roomBuilding
	 * @return
	 */
	public static String isRoomValid(String roomName, Integer roomCapacity, String roomBuilding) {
		String errmsg = "";
		if (roomName == null || roomName.length() == 0) {
			errmsg += "Invalid Room Name\n";
		}
		if (roomCapacity == null || roomCapacity < 0) {
			errmsg += "Invalid Capacity\n";
		} 
		if (roomBuilding == null || roomBuilding.length() == 0) {
			errmsg += "Invalid Building\n";
		}

		return errmsg;
	}

	/**
	 * Vérifie la validité d'un créneau
	 * @return
	 */
	public static String isTimeSlotValid(LocalTime start, LocalTime end, LocalDate day) {
		String errmsg = "";
		if(start.isAfter(end)) {
			errmsg += "Invalid Times";
		}
		if (day == null) {
			errmsg += "Invalid Day\n";
		}

		return errmsg;
	}


	/**
	 * Vérifie la validité d'une réservation
	 * 
	 * @param p
	 * @param r
	 * @param t
	 * @return
	 */
	public static String isReservationValid(Person p, Room r, TimeSlot t, List<Reservation> reservationData) {
		String errmsg = "";
		// Vérification des champs
		if(p == null) {
			errmsg += "Please select a reserver\n";
		}
		if(r == null) {
			errmsg += "Please select a room\n";
		}
		if(t == null) {
			errmsg += "Please select a time slot\n";
		}

		// Check si une réservation aux mêmes date de la même salle existe déjà
		for(Reservation res: reservationData) {
			if(res.getRoom().getId() == r.getId() // Même salle
					&& res.getTimeSlot().getDay().equals(t.getDay()) // Même jour
					&& (((res.getTimeSlot().getEnd().compareTo(t.getStart()) > 0) && (t.getEnd().compareTo(res.getTimeSlot().getStart()) > 0))
							|| ((res.getTimeSlot().getStart().compareTo(t.getEnd()) > 0) && (t.getStart().compareTo(res.getTimeSlot().getEnd()) > 0))
							|| res.getTimeSlot().getId() == t.getId())) { // Edition
				errmsg += "The room is already reserved on this time slot\n";
			}
		}
		return errmsg;
	}
	
	
	/**
	 * Vérifie s'il y a des réservations faisant référence à la personne à supprimer
	 * @param p
	 * @param reservationData
	 * @return
	 */
	public static boolean isPersonDeleteSafe(Person p, List<Reservation> reservationData) {
		for(Reservation r: reservationData) {
			if(p.getId() == r.getPerson().getId()) 
				return false;
		}	
		return true;
	}

	/**
	 * Vérifie s'il y a des réservations faisant référence à la salle à supprimer
	 * @param r
	 * @param reservationData
	 * @return
	 */
	public static boolean isRoomDeleteSafe(Room r, List<Reservation> reservationData) {		
		for(Reservation res: reservationData) {
			if(r.getId() == res.getRoom().getId()) 
				return false;
		}	
		return true;
	}
	
	/**
	 * Vérifie s'il y a des réservations faisant référence au créneau à supprimer
	 * @param t
	 * @param reservationData
	 * @return
	 */
	public static boolean isTSDeleteSafe(TimeSlot t, List<Reservation> reservationData) {
		for(Reservation r: reservationData) {
			if(t.getId() == r.getTimeSlot().getId()) 
				return false;
		}	
		return true;
	}

}
