package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.exception.ImpossibleStartingException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Vector;

/**
 * 
 * Cette interface fournit les différents services afin de visualiser les évènements survenus sur le réseau
 * 
 * @author iGo
 * 
 */
public abstract class EventInfoNetworkWatcher extends Observable {

	/**
	 * Démarre la surveillance des évènements
	 * 
	 * @throws ImpossibleStartingException
	 */
	public abstract void startWatching() throws ImpossibleStartingException;

	/**
	 * Arrête la surveillance des évènements
	 */
	public abstract void stopWatching();

	/**
	 * Permet d'obtenir le status de la surveillance des évènements
	 * 
	 * @return the actual status
	 * @uml.property name="status"
	 * @uml.associationEnd readOnly="true" inverse="eventInfoNetworkWatcher:iGoMaster.EventInfoNetWorkWatcherStatus"
	 */
	public abstract EventInfoNetWorkWatcherStatus getStatus();

	/**
	 * Applique les changements sur un graphe
	 * 
	 * @param graph le graphe sur lequel les changements vont être appliqués
	 */
	public abstract void applyInfo(GraphNetworkBuilder graph);

	/**
	 * 
	 * Permet d'obtenir toutes les nouvelles informations sur le réseau.
	 * Si le EventInfoNetworkWatcher est en train de charger un EventInfo en même temps,
	 * Cette fonction sera bloquante jusqu'à ce que le chargement soit effectué.
	 * 
	 * @return Une collection avec les nouveaux évènements, ou null si aucun évènement n'est survenu
	 */
	public Collection<EventInfo> getNewEventInfo() {
		return new Vector<EventInfo>(eventInfosNotApplied);
	}

	/**
	 * @uml.property name="eventInfosNotApplied"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true"
	 *                     inverse="eventInfoNetworkWatcherInFolder:iGoMaster.EventInfo"
	 */
	protected LinkedList<EventInfo> eventInfosNotApplied;

}
