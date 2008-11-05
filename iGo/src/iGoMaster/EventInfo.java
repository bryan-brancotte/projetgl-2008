package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
/**
 *  
 * @author iGo
 */
public interface EventInfo {

	/**
	 * Retourne l'identifiant de l'�v�nement
	 * 
	 * @return l'identifiant unique repr�sentant un �v�nement
	 */
	public abstract int getId();

	/**
	 * Applique le changement au r�seau
	 * 
	 * @param graph
	 *            le r�seau en question
	 */
	public abstract void applyInfo(GraphNetworkBuilder graph);

	/**
	 * Retourne le message contenu dans l'�v�nement
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
