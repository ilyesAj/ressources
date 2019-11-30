package isty.iatic5.arlo.res.view;

import java.util.UUID;

import isty.iatic5.arlo.res.facade.ResourcesInterface;
import isty.iatic5.arlo.res.model.InvalidInputException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Controleur pour RoomEdit.fxml
 * 
 * (Fenetre modale d'ajout de salle) 
 * 
 * @author Clément Lefevre
 *
 */
public class RoomCreateController {

	@FXML
	private TextField roomNameField;

	@FXML
	private TextField roomCapacityField; 

	@FXML
	private TextField roomBuildingField;

	/**
	 * Stage de la fenêtre modale
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

	}

	/**
	 * Clic sur le bouton OK
	 */
	@FXML 
	private void handleOk() {
		try {
			resourcesManager.createRoom(UUID.randomUUID().toString(), roomNameField.getText(), 
					Integer.parseInt(roomCapacityField.getText()), roomBuildingField.getText());

			// Fermeture de la fenêtre
			okClicked = true;
			editStage.close();
		} catch (NumberFormatException e) { // Exception en cas de saisie invalid
			showAlert("Invalid capacity");
		} catch (InvalidInputException e) {
			showAlert(e.getMessage());
		}
	}

	/**
	 * Affiche une alerte en cas d'erreur
	 * @param errmsg
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
	 * Renvoie vrai si le bouton OK a été cliqué
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}


	/**
	 * Affichage de la fenêtre modale 
	 * @param editStage
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
