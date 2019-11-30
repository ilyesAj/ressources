package isty.iatic5.arlo.res.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import isty.iatic5.arlo.res.util.LocalDateAdapter;
import isty.iatic5.arlo.res.util.LocalTimeAdapter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe représentant un créneau
 * 
 * @author Clément Lefevre
 *
 */
public class TimeSlot {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id.get() == null) ? 0 : id.get().hashCode());
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
		TimeSlot other = (TimeSlot) obj;
		if (id.get() == null) {
			if (other.id.get() != null)
				return false;
		} else if (!id.get().equals(other.id.get()))
			return false;
		return true;
	}

	private final StringProperty id;
	private final ObjectProperty<LocalTime> start; 
	private final ObjectProperty<LocalTime> end;	
	private final ObjectProperty<LocalDate> day;
	
	/**
	 * Constructor
	 * @param id identifiant
	 * @param start début du créneau
	 * @param end fin du créneau
	 * @param day date du créneau
	 */
	public TimeSlot(String id, LocalTime start, LocalTime end, LocalDate day) {
		this.id = new SimpleStringProperty(id);	
		this.start = new SimpleObjectProperty<LocalTime>(start);
		this.end = new SimpleObjectProperty<LocalTime>(end);
		this.day = new SimpleObjectProperty<LocalDate>(day);
	}
	
	/**
	 * Constructeur par défaut
	 */
	public TimeSlot() {
		this(null, null, null, null);
	}
	
	@XmlID
	public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }
    
    public StringProperty idProperty() {
        return id;
    }
    
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    public LocalTime getStart() {
        return start.get();
    }

    public void setStart(LocalTime start) {
        this.start.set(start);
    }
    
    public ObjectProperty<LocalTime> startProperty() {
        return start;
    }
    
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    public LocalTime getEnd() {
        return end.get();
    }

    public void setEnd(LocalTime end) {
        this.end.set(end);
    }
    
    public ObjectProperty<LocalTime> endProperty() {
        return end;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDay() {
        return day.get();
    }

    public void setDay(LocalDate day) {
        this.day.set(day);
    }
    
    public ObjectProperty<LocalDate> dayProperty() {
        return day;
    }
}
