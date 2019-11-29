package isty.iatic5.arlo.res.model;

/**
 * Exception indiquant qu'un élément ne peut être supprimé, car une référence existe
 * 
 * @author Clément Lefevre
 *
 */
public class ExistingReferenceException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param type la classe de l'objet
	 * @param id l'ID de l'objet
	 */
	public ExistingReferenceException(String type, String id) {
		super(type + " of id " + id + " is referenced in a reservation and cannot be delete.");
	}
}
