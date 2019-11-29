package isty.iatic5.arlo.res.model;

/**
 * Exception levée en cas d'invalidité des champs saisis
 * 
 * @author Clément Lefevre
 *
 */
public class InvalidInputException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public InvalidInputException(String s) {
		super(s);
	}
}
