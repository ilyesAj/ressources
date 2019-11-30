package isty.iatic5.arlo.res.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Controleur pour RootLayout.fxml
 * 
 * @author Clément Lefevre
 *
 */
public class RootLayoutController {

	/**
	 * Ouvre un modal "A propos"
	 */
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("ResourcesApp");
		alert.setHeaderText("Authors");
		alert.setContentText("Clément LEFEVRE, Lois JULLIEN, Alessandro DONA, Jérémy JULLIENNE");

		alert.showAndWait();
	}

	/**
	 * Ferme l'aplication
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}
	
}
