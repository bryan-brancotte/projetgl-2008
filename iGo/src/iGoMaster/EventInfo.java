package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
/**
 *  Interface permettant de n'avoir a traiter les evenements que comme des EventInfo,
 *  Utile lorsque l'on veut recuperer le message de chaque evenement pour l'IHM
 *  
 * @author iGo
 */
public interface EventInfo {

	/**
	 * Retourne l'identifiant de l'evenement
	 * 
	 * @return l'identifiant representant un evenement
	 */
	public abstract int getId();

	/**
	 * Applique le changement au reseau
	 * 
	 * @param graph
	 *            le reseau en question
	 */
	public abstract void applyInfo(GraphNetworkBuilder graph);

	/**
	 * Retourne le message contenu dans l'evenement
	 * 
	 * @return le message
	 */
	public abstract String getMessage();

	/**
	 * Renvoie le type de l'evenement
	 * @return Retourne the kindEventInfoNetwork.
	 */
	public abstract KindEventInfoNetwork getKindEventInfoNetwork();
	
	/**
	 * Dit si l'evenement a ete applique
	 * @return True si l'evenement a ete applique
	 */
	public abstract boolean isApplied();
	
	/**
	 * Retourne l'identifiant de la route
	 * 
	 * @return l'identifiant de la route
	 */
	public abstract String getIdRoute();
	

	/**
	 * Retourne l'identifiant de la station
	 * 
	 * @return l'identifiant de la station
	 */
	public abstract int getIdStation();

}
