package isty.iatic5.arlo.res.model;

import javax.xml.bind.annotation.XmlID;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * Classe représentant une salle
 * 
 * @author Clément Lefevre
 *
 */
public class Room {
	private final StringProperty id;
	private final StringProperty name;
	private final IntegerProperty capacity;
	private final StringProperty building;
	

	/**
	 * Constructeur
	 * @param id
	 * @param name
	 * @param capacity
	 * @param building
	 */
	public Room(String id, String name, Integer capacity, String building) {
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.capacity = new SimpleIntegerProperty();
		this.capacity.setValue(capacity);	
		this.building = new SimpleStringProperty(building);
	}
	
	/**
	 * Constructeur par défaut
	 */
	public Room() {
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
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public Integer getCapacity() {
		return capacity.get();
	}
	
	public void setCapacity(Integer capacity) {
		this.capacity.set(capacity);
	}
	
	public IntegerProperty capacityProperty() {
		return capacity;
	}
	
	public String getBuilding() {
		return building.get();
	}
	
	public void setBuilding(String building) {
		this.building.set(building);
	}
	
	public StringProperty buildingProperty() {
		return building;
	}
}
