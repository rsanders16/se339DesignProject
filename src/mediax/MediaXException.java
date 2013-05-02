package mediax;

public class MediaXException extends Exception {

	/**
	 * Needed for proper inheritance from Parent class MediaXException
	 */
	private static final long serialVersionUID = 1L;

	public MediaXException(final String string) {
		super(string);
	}

}
