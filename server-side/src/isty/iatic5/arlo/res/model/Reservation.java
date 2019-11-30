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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((person.get() == null) ? 0 : person.get().hashCode());
		result = prime * result + ((room.get() == null) ? 0 : room.get().hashCode());
		result = prime * result + ((timeSlot.get() == null) ? 0 : timeSlot.get().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (person.get().getId() == null) {
			if (other.person.get().getId() != null)
				return false;
		} else if (!person.get().getId().equals(other.person.get().getId()))
			return false;
		if (room.get().getId() == null) {
			if (other.room.get().getId() != null)
				return false;
		} else if (!room.get().getId().equals(other.room.get().getId()))
			return false;
		if (timeSlot.get() == null) {
			if (other.timeSlot.get().getId() != null)
				return false;
		} else if (!timeSlot.get().getId().equals(other.timeSlot.get().getId()))
			return false;
		return true;
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
