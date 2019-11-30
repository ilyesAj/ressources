package isty.iatic5.arlo.res;

import isty.iatic5.arlo.res.facade.ResourcesFacade;
import isty.iatic5.arlo.res.facade.ResourcesInterface;
import isty.iatic5.arlo.res.view.MainGUI;

/**
 * Point d'entrée du programme 
 * Instancie l'IHM et l'objet exposant les fonctionnalités
 *
 */
public class MainApp {
	
	/**
	 * Fonction main, point d'entrée du programme
	 * @param args
	 */
	public static void main(String[] args) {
		// Instanciation de l'objet exposant les fonctionnalités
		ResourcesInterface resourcesManager = new ResourcesFacade();
		
		// Instanciation de l'IHM
		MainGUI mainGUI = new MainGUI();
		// Passe la référence de l'objet exposant les ressources à l'IHM
		mainGUI.setResourcesManager(resourcesManager);
		mainGUI.show();
	}
}
