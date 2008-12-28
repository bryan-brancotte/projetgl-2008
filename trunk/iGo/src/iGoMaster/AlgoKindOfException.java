package iGoMaster;


/**
 * Associe les différents types d'erreur pouvant être rencontrés par l'algorithme à une valeur entière. 
 * Si l'algorithme échoue, l'ihm pourra ainsi fournir à l'utilisateur un message d'erreur adapté.
 * 
 * @author elodie
 *
 */

public enum AlgoKindOfException {
	
	
	ServiceNotAccessibleException(),
	StationNotAccessibleException(),
	
	NoRouteForStationException(),
	
	StationNotOnRoadException(),
	
	VoidPathException(),
	
	NonValidOriginException(),
	NonValidDestinationException(),
	
	UnknownException(),
	EverythingFine();
	
}