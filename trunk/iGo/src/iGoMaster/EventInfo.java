package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
/**
 *  
 * @author iGo
 */
public interface EventInfo {

	/**
	 * Retourne l'identifiant de l'évènement
	 * 
	 * @return l'identifiant unique représentant un évènement
	 */
	public abstract int getId();

	/**
	 * Applique le changement au réseau
	 * 
	 * @param graph
	 *            le réseau en question
	 */
	public abstract void applyInfo(GraphNetworkBuilder graph);

	/**
	 * Retourne le message contenu dans l'évènement
	 * 
	 * @return le message
	 */
	public abstract String getMessage();

	/**
	 * 
	 * @return Retourne the kindEventInfoNetwork.
	 * @uml.property name="kindEventInfoNetwork"
	 * @uml.associationEnd readOnly="true" inverse="eventInfo:iGoMaster.KindEventInfoNetwork"
	 */
	public abstract KindEventInfoNetwork getKindEventInfoNetwork();

}
