package isty.iatic5.arlo.res.model;

import javax.xml.bind.annotation.XmlIDREF;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Classe représentant une réservation
 *  
 * @author Clément Lefevre
 *
 */
public class Reservation {
	private final ObjectProperty<TimeSlot> timeSlot;
	private final ObjectProperty<Person> person;
	private final ObjectProperty<Room> room;
	
	/** 
	 * Constructor
	 * 
	 * @param timeSlot
	 * @param person
	 * @param room
	 */
	public Reservation(TimeSlot timeSlot, Person person, Room room) {
		this.timeSlot = new SimpleObjectProperty<TimeSlot>(timeSlot);
		this.person = new SimpleObjectProperty<Person>(person);
		this.room = new SimpleObjectProperty<Room>(room);
	}
	
	/**
	 * Constructeur par défaut
	 */
	public Reservation() {
		this(null, null, null);
	}
    
    @XmlIDREF
    public TimeSlot getTimeSlot() {
        return timeSlot.get();
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot.set(timeSlot);
    }
    
    public ObjectProperty<TimeSlot> timeSlotProperty() {
        return timeSlot;
    }
    
    @XmlIDREF
    public Person getPerson() {
        return person.get();
    }

    public void setPerson(Person person) {
        this.person.set(person);
    }
    
    public ObjectProperty<Person> personProperty() {
        return person;
    }
    
    @XmlIDREF
    public Room getRoom() {
        return room.get();
    }

    public void setRoom(Room room) {
        this.room.set(room);
    }
    
    public ObjectProperty<Room> roomProperty() {
        return room;
    }
	
}
