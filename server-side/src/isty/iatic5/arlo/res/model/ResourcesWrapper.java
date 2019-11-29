package isty.iatic5.arlo.res.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe permettant de Marshal/Unmarshal les données depuis/vers un fichier XML
 * 
 */
@XmlRootElement(name = "resources")
public class ResourcesWrapper {
	
	private List<Room> rooms;
	
	private List<Person> persons;
	
	private List<TimeSlot> timeSlots;
	
	private List<Reservation> reservations;

	
	@XmlElement(name = "room")
	public List<Room> getRooms() {
		return rooms;
	}
	
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	@XmlElement(name = "person")
	public List<Person> getPersons() {
		return persons;
	}
	
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	@XmlElement(name = "timeslot") 
	public List<TimeSlot> getTimeSlots() {
		return timeSlots; 
	}
	
	public void setTimeSlots(List<TimeSlot> timeSlots) {
		this.timeSlots = timeSlots;
	}
	
	@XmlElement(name = "reservation")
	public List<Reservation> getReservations() {
		return this.reservations;
	}
	
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
}
