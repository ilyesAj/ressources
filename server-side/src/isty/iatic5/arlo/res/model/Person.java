package isty.iatic5.arlo.res.model;

import javax.xml.bind.annotation.XmlID;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe représentant une personne
 * 
 * @author Clément Lefevre
 *
 */
public class Person {
	private final StringProperty id;
	private final StringProperty firstName;
	private final StringProperty lastName;
	private final StringProperty email;
	private final ObjectProperty<Status> status;
	
	/**
	 * Constructeur
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param status
	 */
	public Person(String id, String firstName, String lastName, String email, Status status) {
		this.id = new SimpleStringProperty(id);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.email = new SimpleStringProperty(email);
		this.status = new SimpleObjectProperty<Status>(status);
	}
	
	/**
	 * Constructeur par défaut
	 */
    public Person() {
    	this(null, null, null, null, null);
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
    
	public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    
    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getEmail() {
    	return email.get();
    }

    public void setEmail(String email) {
    	this.email.set(email);
    }
    
    public StringProperty emailProperty() {
    	return email;
    }
    
    public Status getStatus() {
    	return this.status.get();
    }
    
    public void setStatus(Status status) {
    	this.status.set(status);
    }
    
    public ObjectProperty<Status> statusProperty() {
    	return status;
    }
}
