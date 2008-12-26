package graphNetwork.exception;

/**
 * Erreur indiquant que la station n'est pas sur la route spécifié
 * 
 * @author iGo
 */
public class StationNotOnRoadException extends Exception {

//	public StationNotOnRoadException() {
//		super();
//	}

	public StationNotOnRoadException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -386842613932156679L;

}
