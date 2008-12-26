package iGoMaster;

/**
 * Associe les différents types d'erreur pouvant être rencontrés par l'algorithme à une valeur entière. 
 * Si l'algorithme échoue, l'ihm pourra ainsi fournir à l'utilisateur un message d'erreur adapté.
 * 
 * @author elodie
 *
 */

public enum AlgoKindOfException {
	
	VoidPathException(0),
	ServiceNotAccessibleException(1),
	StationNotAccessibleException(2),
	NoRouteForStationException(3),
	StationNotOnRoadException(4),
	NonValidPathException(5),
	UnknownException(6),
	EverythingFine(7);
	
	protected int value;

	private AlgoKindOfException(int value) {
		this.value = value;
	}

	public int getAlgoKindOfException() {
		return this.value;
	}
}
