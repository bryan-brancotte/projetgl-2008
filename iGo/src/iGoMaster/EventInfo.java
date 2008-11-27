package iGoMaster;

import graphNetwork.GraphNetworkFactory;
/**
 *  
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
	public abstract void applyInfo(GraphNetworkFactory graph);

	/**
	 * Retourne le message contenu dans l'evenement
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
