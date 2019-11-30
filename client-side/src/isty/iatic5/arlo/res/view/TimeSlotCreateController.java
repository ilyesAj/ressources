package isty.iatic5.arlo.res.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import isty.iatic5.arlo.res.facade.ResourcesInterface;
import isty.iatic5.arlo.res.model.InvalidInputException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controleur pour TimeSlotEdit.fxml
 * 
 * (Fenetre modale d'ajout de timeslot) 
 *
 * @author Clement Lefevre
 *
 */
public class TimeSlotCreateController {

	@FXML
	private TextField startField;
	
	@FXML
	private TextField endField; 
	
	@FXML
	private DatePicker dayPicker;
	
	/**
	 * Stage de la fenetre modale
	 */
	private Stage editStage;
	
	/**
	 * Interface exposant les fonctionnalités
	 */
	private ResourcesInterface resourcesManager;
		
	/**
	 * Booleen pour la gestion de la validation
	 */
	private boolean okClicked = false;
	
		
	/**
	 * Initialise le controleur 
	 */
	@FXML
	private void initialize() {
		dayPicker.setValue(LocalDate.now());
	}
	
	/**
	 * Clic sur le bouton OK
	 */
	@FXML 
    private void handleOk() {
		try {
        	// Création du créneau
        	resourcesManager.createTimeSlot(UUID.randomUUID().toString(), dayPicker.getValue(), 
        			LocalTime.parse(startField.getText()), LocalTime.parse(endField.getText()));
        	
        	// Fermeture de la fenêtre
            okClicked = true;
            editStage.close();
        } catch (DateTimeParseException e) { // Exception en cas de saisie incorrecte
        	showAlert("Invalid times");
        } catch (InvalidInputException e) {
        	showAlert(e.getMessage());
        }
    }
	
	/**
	 * Affiche une alerte en cas d'erreur
	 * @param errmsg message indiquant les champs invalides
	 */
	public void showAlert(String errmsg) {
		Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(editStage);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errmsg);
        alert.showAndWait();
	}
	
	/**
	 * Clic sur le bouton Cancel
	 */
	@FXML 
    private void handleCancel() {
		editStage.close();
    }
	
    /**
     * @return un booleen si le bouton OK est clique
     */
    public boolean isOkClicked() {
        return okClicked;
    }

	/**
	 * Affichage de la fenêtre modale 
	 * @param editStage le nouveau stage
	 */
	public void setEditStage(Stage editStage) {
		this.editStage = editStage;
	}
	
	/**
	 * Référence à l'interface
	 * @param resourcesManager 
	 */
	public void setResourcesManager(ResourcesInterface resourcesManager) {
		this.resourcesManager = resourcesManager;
	}
	
}
