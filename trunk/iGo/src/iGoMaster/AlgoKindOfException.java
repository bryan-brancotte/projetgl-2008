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
	 * 
	 * Le services, que l'ihm pourra récupérer dans infoPathAsked, n'est pas accessible avec les préférences utilisateurs courantes
	 */
	ServiceNotAccessible(),
	
	/**
	 * La stations, que l'ihm pourra récupérer dans infoPathAsked, n'est pas accessible avec les préférences utilisateurs courantes
	 */
	StationNotAccessible(),
	
	/**
	 * La stations, que l'ihm pourra récupérer dans infoPathAsked, ne se trouve sur aucune route capable de satisfaire les préférences de l'utilisateur.
	 */
	RoutesNotAccessible(),
	
	/**
	 * L'entité graphNetwork indique qu'une station n'est pas sur la route spécifiée
	 */
	StationNotOnGraphNetworkRoad(),
	
	/**
	 * Il n'y a aucune solution à l'algorithme mais on ne sait pas pourquoi
	 */
	NoSolution(),
	
	/**
	 * 
	 * L'utilisateur a demandé un service à toutes les stations mais ce dernier
	 * n'est pas présent dans la station de départ
	 * 
	 */
	NonValidOrigin(), 
	
	/**
	 * L'utilisateur a demandé un service à toutes les stations mais ce dernier
	 * n'est pas présent dans la station d'arrivée
	 */
	NonValidDestination(),
	
	/**
	 * L'algorithme a renvoyé une erreur non repertoriée
	 */
	UndefinedError(),
	
	/**
	 * L'algorithme de résolution du chemin n'a rencontré aucune erreur
	 */
	EverythingFine();

}
