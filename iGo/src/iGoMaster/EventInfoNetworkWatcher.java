package iGoMaster;

import graphNetwork.GraphNetwork;
import iGoMaster.exception.ImpossibleStartingException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * 
 * Cette classe abstraite fournit les differents services afin de visualiser les evenements survenus sur le reseau
 * 
 * Une classe héritant de la classe abstraite EventInfoNetworkWatcher doit notifier le master
 * après chaque nouvelle mise à jour du réseau. Pour cela on pourra utiliser la méthode 
 * notifyObservers(Object o). Un object null sera passé en argument.
 * 
 */
public abstract class EventInfoNetworkWatcher extends Observable {

	/**
	 * Demarre la surveillance des evenements
	 * 
	 * @throws ImpossibleStartingException
	 */
	public abstract void startWatching() throws ImpossibleStartingException;

	/**
	 * Arrete la surveillance des evenements
	 */
	public abstract void stopWatching();

	/**
	 * Permet d'obtenir le status de la surveillance des evenements
	 * 
	 * @return the actual status
	 */
	public abstract EventInfoNetWorkWatcherStatus getStatus();

	/**
	 * Applique les changements sur un graphe
	 * 
	 * @param graph
	 *            le graphe sur lequel les changements vont être appliqués
	 */
	public abstract void applyInfo(GraphNetwork graph);

	/**
	 * 
	 * Permet d'obtenir toutes les nouvelles informations sur le reseau. Si le EventInfoNetworkWatcher est en train de
	 * charger un EventInfo en meme temps, Cette fonction sera bloquante jusqu'a ce que le chargement soit effectue.
	 * 
	 * @return Une collection avec les nouveaux evenements, ou null si aucun evenement n'est survenu
	 */
	public Collection<EventInfo> getNewEventInfo() {
		return new Vector<EventInfo>(eventInfosNotApplied);
	}

	/**
	 * Evenements non traites
	 */
	protected LinkedList<EventInfo> eventInfosNotApplied;

	/**
	 */
	public void setChanged() {
	}


}
