package isty.iatic5.arlo.res.view;

import java.io.IOException;

import isty.iatic5.arlo.res.facade.ResourcesInterface;
import isty.iatic5.arlo.res.model.NullElementException;
import isty.iatic5.arlo.res.model.Reservation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * IHM Principale, gère l'organisation des différentes fenêtres
 * 
 * @author Clément Lefevre
 *
 */
public class MainGUI extends Application{
	
	/**
	 * La fenêtre
	 */
	private Stage primaryStage;

	/**
     * Le Root Layout (racine de la fenêtre)
     */
    private BorderPane rootLayout;
    
    /**
     * L'interface permettant d'accéder aux fonctionnalités 
     */
	private static ResourcesInterface resourcesManager;

	/**
	 * Point d'entrée de l'IHM
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Création de la fenêtre
	    this.primaryStage = primaryStage;
	    
	    // Ajout du titre et de l'icone
        this.primaryStage.setTitle("Resources Management");
        this.primaryStage.getIcons().add(new Image("icons/manageIcon.png"));	
        
        // Initiatilisation de la racine (MenuBar)
        initRootLayout();
        
        // Initialisation de la vue des ressources
        showResourcesOverview();
	}
	
	
	/**
     * Constructeur
     */
    public MainGUI() {
    	
    }

    /**
     * Initialise le Root Layout (racine de la fenêtre)
     */
    public void initRootLayout() {
        try {
            // Charge le fichier FXML correspondant
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGUI.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Affiche la scène (le contenu)
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            // Affiche la fenêtre
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Affiche la vue des ressources 
     */
    public void showResourcesOverview() {
        try {
            // Charge le fichier FXML correspondant
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGUI.class.getResource("ResourcesOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            // Place l'élément au centre de la fenêtre
            rootLayout.setCenter(personOverview);
            
            // Initialise le controlleur
            ResourcesOverviewController controller = loader.getController();
            controller.setMainGUI(this);     
            controller.setResourcesManager(resourcesManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Affiche la fenêtre modale d'ajout de personne 
     * @return
     */
    public boolean showPersonCreate() {
        try {
            // Charge le fichier FXML correspondant
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGUI.class.getResource("PersonCreate.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Création d'une nouvelle fenêtre modale
            Stage editStage = new Stage();
            editStage.setTitle("Create Person");
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            editStage.setScene(scene);

            // Initialise le controlleur
            PersonCreateController controller = loader.getController();
            controller.setEditStage(editStage);
            controller.setResourcesManager(resourcesManager);

            // Affiche la fenêtre jusqu'à ce que l'utilisateur la ferme
            editStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    /**
     * Affiche la fenêtre modale d'ajout de salle 
     * @return
     */
    public boolean showRoomCreate() {
        try {
            // Charge le fichier FXML correspondant
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGUI.class.getResource("RoomCreate.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Création d'une nouvelle fenêtre modale
            Stage editStage = new Stage();
            editStage.setTitle("Create Room");
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            editStage.setScene(scene);

            // Initialisation du contrôleur
            RoomCreateController controller = loader.getController();
            controller.setEditStage(editStage);
            controller.setResourcesManager(resourcesManager);

            // Affiche la fenêtre jusqu'à ce que l'utilisateur la ferme
            editStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Affiche la fenêtre modale d'ajout de créneau 
     * @return
     */
    public boolean showTimeSlotCreate() {
        try {
            // Chargement du fichier FXML correspondant
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGUI.class.getResource("TimeSlotCreate.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Création d'une nouvelle fenêtre modale 
            Stage editStage = new Stage();
            editStage.setTitle("Create Time Slot");
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            editStage.setScene(scene);

            // Initialisation du contrôleur
            TimeSlotCreateController controller = loader.getController();
            controller.setEditStage(editStage);
            controller.setResourcesManager(resourcesManager);
           
            // Affiche la fenêtre jusqu'à ce que l'utilisateur la ferme
            editStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Affiche la fenêtre modale d'ajout de reservations 
     * @return
     */
    public boolean showReservationCreate() {
        try {
            // Chargement du fichier FXML correspondant
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGUI.class.getResource("ReservationCreate.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Création d'une nouvelle fenêtre modale 
            Stage editStage = new Stage();
            editStage.setTitle("Create Reservation");
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            editStage.setScene(scene);

            // Initialisation du contrôleur
            ReservationCreateController controller = loader.getController();
            controller.setEditStage(editStage);
            controller.setResourcesManager(resourcesManager);
            
            // Affiche la fenêtre jusqu'à ce que l'utilisateur la ferme
            editStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Affiche la fenêtre modale d'édition de reservations 
     * @return
     */
    public boolean showReservationEdit(Reservation r) throws NullElementException {
        try {
            // Chargement du fichier FXML correspondant
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGUI.class.getResource("ReservationEdit.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Création d'une nouvelle fenêtre modale 
            Stage editStage = new Stage();
            editStage.setTitle("Edit Reservation");
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            editStage.setScene(scene);

            // Initialisation du contrôleur
            ReservationEditController controller = loader.getController();
            controller.setEditStage(editStage);
            controller.setResourcesManager(resourcesManager);
            controller.setReservation(r);
            
            // Affiche la fenêtre jusqu'à ce que l'utilisateur la ferme
            editStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Lance l'IHM JavaFX
     */
    public void show(){
        launch();
    }
    
    /**
     * Renvoie le Stage (fenêtre)
     */
    public Stage getPrimaryStage() {
    	return primaryStage;
    }

    /**
     * Donne une référence au resourcesManager
     * @param resourcesManager
     */
	public void setResourcesManager(ResourcesInterface resourcesManager) {
		MainGUI.resourcesManager = resourcesManager;
	}


}
