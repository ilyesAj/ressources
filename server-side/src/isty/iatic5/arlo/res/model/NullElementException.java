package isty.iatic5.arlo.res.model;

/**
 * Exception levée en cas d'élément valant null
 *
 */
public class NullElementException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NullElementException(String s) {
		super(s);
	}

}
