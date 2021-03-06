package graphNetwork.exception;

/**
 * Erreur indiquant qu'on viole l'unicité d'un identifiant : on tente apr exemple de créer deux station avec le même id.
 * 
 * @author iGo
 */
public class ViolationOfUnicityInIdentificationException extends Exception {

//	public ViolationOfUnicityInIdentificationException() {
//		super();
//	}

	public ViolationOfUnicityInIdentificationException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5789299578500115019L;

}
