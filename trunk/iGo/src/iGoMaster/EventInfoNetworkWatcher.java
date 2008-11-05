package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.exception.ImpossibleStartingException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Vector;

/**
 * 
 * Cette interface fournit les diff�rents services afin de visualiser les �v�nements survenus sur le r�seau
 * 
 * @author iGo
 * 
 */
public abstract class EventInfoNetworkWatcher extends Observable {

	/**
	 * D�marre la surveillance des �v�nements
	 * 
	 * @throws ImpossibleStartingException
	 */
	public abstract void startWatching() throws ImpossibleStartingException;

	/**
	 * Arr�te la surveillance des �v�nements
	 */
	public abstract void stopWatching();

	/**
	 * Permet d'obtenir le status de la surveillance des �v�nements
	 * 
	 * @return the actual status
	 * @uml.property name="status"
	 * @uml.associationEnd readOnly="true" inverse="eventInfoNetworkWatcher:iGoMaster.EventInfoNetWorkWatcherStatus"
	 */
	public abstract EventInfoNetWorkWatcherStatus getStatus();

	/**
	 * Applique les changements sur un graphe
	 * 
	 * @param graph le graphe sur lequel les changements vont �tre appliqu�s
	 */
	public abstract void applyInfo(GraphNetworkBuilder graph);

	/**
	 * 
	 * Permet d'obtenir toutes les nouvelles informations sur le r�seau.
	 * Si le EventInfoNetworkWatcher est en train de charger un EventInfo en m�me temps,
	 * Cette fonction sera bloquante jusqu'� ce que le chargement soit effectu�.
	 * 
	 * @return Une collection avec les nouveaux �v�nements, ou null si aucun �v�nement n'est survenu
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
