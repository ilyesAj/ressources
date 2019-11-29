package isty.iatic5.arlo.res.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import isty.iatic5.arlo.res.facade.ResourcesInterface;
import isty.iatic5.arlo.res.model.Person;
import isty.iatic5.arlo.res.model.Reservation;
import isty.iatic5.arlo.res.model.ResourcesWrapper;
import isty.iatic5.arlo.res.model.Room;
import isty.iatic5.arlo.res.model.TimeSlot;


/**
 * Classe permettant de marshal/unmarshal les données depuis/vers le fichier XML
 *
 */
public class DatabaseManager {

	/**
	 * Référence au resourcesManager
	 */
	ResourcesInterface resourcesManager;

	/**
	 * Fichier au sein duquel sont stockées les données
	 */
	private File dataFile; 

	/**
	 * Constructeur
	 */
	public DatabaseManager() {
		setupDataFile();
	}


	/**
	 * Charge les ressources issues du fichier XML
	 * 
	 */
	public void loadResources() {
		try {
			JAXBContext context = JAXBContext
					.newInstance(ResourcesWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			ResourcesWrapper resourcesWrapper = (ResourcesWrapper) um.unmarshal(dataFile);

			resourcesManager.getPersonData().clear();
			resourcesManager.getRoomData().clear();
			resourcesManager.getTimeSlotData().clear();
			resourcesManager.getReservationData().clear();

			List<Person> loadedPersons = resourcesWrapper.getPersons();
			List<Room> loadedRooms = resourcesWrapper.getRooms();
			List<TimeSlot> loadedTimeSlots = resourcesWrapper.getTimeSlots();
			List<Reservation> loadedReservations = resourcesWrapper.getReservations();

			if(loadedPersons != null) 
				resourcesManager.getPersonData().addAll(resourcesWrapper.getPersons());

			if(loadedRooms != null) 
				resourcesManager.getRoomData().addAll(resourcesWrapper.getRooms());

			if(loadedTimeSlots != null) 
				resourcesManager.getTimeSlotData().addAll(resourcesWrapper.getTimeSlots());

			if(loadedReservations != null)
				resourcesManager.getReservationData().addAll(resourcesWrapper.getReservations());

		} catch (Exception e) {
			System.out.println("No resources to load.");
		}
	}

	/**
	 * Sauvegarde les ressources dans le fichier XML
	 * 
	 */
	public void saveResources() {
		try {
			JAXBContext context = JAXBContext
					.newInstance(ResourcesWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			ResourcesWrapper resourcesWrapper = new ResourcesWrapper();
			resourcesWrapper.setRooms(resourcesManager.getRoomData());
			resourcesWrapper.setPersons(resourcesManager.getPersonData());
			resourcesWrapper.setTimeSlots(resourcesManager.getTimeSlotData());
			resourcesWrapper.setReservations(resourcesManager.getReservationData());

			// Marshaling et sauvegarde dans le fichier XML
			m.marshal(resourcesWrapper, dataFile); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Donne une référence au resourcesManager
	 * @param resourcesManager
	 */
	public void setResourcesManager(ResourcesInterface resourcesManager) {
		this.resourcesManager =  resourcesManager;
	}


	/**
	 * Crée le fichier resources.xml s'il n'existe pas
	 */
	public void setupDataFile() {
		Preferences prefs = Preferences.userNodeForPackage(DatabaseManager.class);
		String dataFilePath = prefs.get("dataFilePath", null);
		if(dataFilePath == null) {
			dataFilePath = "data/resources.xml";
			prefs.put("dataFilePath", dataFilePath);
		}
		this.dataFile = new File(dataFilePath);
		
		if(!this.dataFile.exists()) {
			this.dataFile.getParentFile().mkdirs();
			try {
				this.dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
