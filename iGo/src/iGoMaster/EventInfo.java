package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
/**
 *  Interface permettant de n'avoir a traiter les evenements que comme des EventInfo,
 *  Utile lorsque l'on veut recuperer le message de chaque evenement pour l'IHM
 * @author iGo
 */
public interface EventInfo {

	/**
	 * Retourne l'identifiant de l'evenement
	 * 
	 * @return l'identifiant unique representant un evenement
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

}
