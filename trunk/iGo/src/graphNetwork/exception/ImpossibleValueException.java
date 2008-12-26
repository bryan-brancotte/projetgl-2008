package graphNetwork.exception;

/**
 * Erreur indiquant une valeur numérique impossible, comme un coût négatif.
 * 
 * @author "iGo"
 * 
 */
public class ImpossibleValueException extends Exception {

//	public ImpossibleValueException() {
//		super();
//	}

	public ImpossibleValueException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8904895330924907227L;

}
