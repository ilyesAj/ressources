package isty.iatic5.arlo.res.view;

import java.util.UUID;

import isty.iatic5.arlo.res.facade.ResourcesInterface;
import isty.iatic5.arlo.res.model.InvalidInputException;
import isty.iatic5.arlo.res.model.Status;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controleur pour PersonEdit.fxml
 * 
 * (Fenetre modale d'ajout de personne)
 * 
 * @author Clément Lefevre
 *
 */
public class PersonCreateController {

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField; 

	@FXML
	private TextField emailField;

	@FXML 
	private ComboBox<Status> statusBox;

	/**
	 * Stage de la fenetre modale
	 */
	private Stage editStage;

	/**
	 * Interface exposant les fonctionnalites
	 */
	private ResourcesInterface resourcesManager;


	/**
	 * Booléen pour la gestion de la validation
	 */
	private boolean okClicked = false;

	/**
	 * Initialise le controleur 
	 */
	@FXML
	private void initialize() {
		// Ajout des valeurs du statut à la ComboBox
		statusBox.getItems().addAll(Status.values());
	}

	/**
	 * Clic sur le bouton OK
	 */
	@FXML 
	private void handleOk() {
		try {
			resourcesManager.createPerson(UUID.randomUUID().toString(), firstNameField.getText(), lastNameField.getText(), 
					emailField.getText(), statusBox.getValue());

			// Fermeture de la fenetre
			okClicked = true;
			editStage.close();
		} catch (InvalidInputException e) { // Exception en cas de saisie invalide
			// Affiche une alerte
			showAlert(e.getMessage());
		}
	}


	/**
	 * Clic sur le bouton Cancel
	 */
	@FXML 
	private void handleCancel() {
		editStage.close();
	}

	
	/**
	 * Affiche une alerte en cas d'erreur
	 * @param errmsg
	 */
	public void showAlert(String errmsg) {
		// Création de l'alerte
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(editStage);
		alert.setTitle("Invalid Fields");
		alert.setHeaderText("Please correct invalid fields");
		alert.setContentText(errmsg);
		// Affichage
		alert.showAndWait();
	}

	/**
	 * Renvoie vrai si le bouton OK a été cliqué
	 * @return true si le bouton OK a été cliqué, false sinon
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
