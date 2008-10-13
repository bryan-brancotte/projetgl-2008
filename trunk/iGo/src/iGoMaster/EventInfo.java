package iGoMaster;

import graphNetwork.GraphNetworkBuilder;

public interface EventInfo {

	/**
	 * Retourne l'identifiant de l'evenement
	 * 
	 * @return l'identifiant unique représenant un evenement
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
	 * Retourne le message contenue dans l'évenement
	 * 
	 * @return le message
	 */
	public abstract String getMessage();

	/**
	 * 
	 * @return Returns the kindEventInfoNetwork.
	 * @uml.property name="kindEventInfoNetwork"
	 * @uml.associationEnd readOnly="true" inverse="eventInfo:iGoMaster.KindEventInfoNetwork"
	 */
	public abstract KindEventInfoNetwork getKindEventInfoNetwork();

}
