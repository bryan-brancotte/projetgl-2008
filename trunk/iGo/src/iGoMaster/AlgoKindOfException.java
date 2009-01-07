package iGoMaster;

/**
 * Associe les différents types d'erreur pouvant être rencontrés par l'algorithme à une valeur entière. Si l'algorithme
 * échoue, l'ihm pourra ainsi fournir à l'utilisateur un message d'erreur adapté.
 * 
 * @author iGo
 * 
 */

public enum AlgoKindOfException {

	/**
	 * //TODO commentaires
	 */
	ServiceNotAccessibleException(),
	/**
	 * //TODO commentaires
	 */
	StationNotAccessibleException(),
	/**
	 * //TODO commentaires
	 */
	NoRouteForStationException(),
	/**
	 * //TODO commentaires
	 */
	StationNotOnRoadException(),
	/**
	 * //TODO commentaires
	 */
	VoidPathException(),
	/**
	 * //TODO commentaires
	 */
	NonValidOriginException(), NonValidDestinationException(),
	/**
	 * //TODO commentaires
	 */
	UnknownException(),
	/**
	 * //TODO commentaires
	 */
	EverythingFine();

}
