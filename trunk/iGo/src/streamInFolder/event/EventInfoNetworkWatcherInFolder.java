package streamInFolder.event;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.EventInfo;
import iGoMaster.EventInfoNetWorkWatcherStatus;
import iGoMaster.EventInfoNetworkWatcher;
import iGoMaster.exception.ImpossibleStartingException;

import java.util.Collection;
import java.util.Vector;

/**
 * Cette classe permet la surveillance d'un dossier qui contiendra les événements
 *
 */
public class EventInfoNetworkWatcherInFolder extends EventInfoNetworkWatcher {

	protected EventInfoNetWorkWatcherStatus status = EventInfoNetWorkWatcherStatus.UNKNOWN_STATUS;
	
	/**
	 * Constructeur d'EventInfoNetworkWatcherInFolder
	 * @param path Chemin du dossier à surveiller
	 */
	public EventInfoNetworkWatcherInFolder(String path) {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Démarre la surveillance du dossier
	 */
	@Override
	public void startWatching() throws ImpossibleStartingException {
		// TODO Auto-generated method stub
	}

	/**
	 * Arrête la surveillance du dossier
	 */
	@Override
	public void stopWatching() {
		// TODO Auto-generated method stub
	}

	/**
	 * Donne le status de la surveillance
	 */
	@Override
	public EventInfoNetWorkWatcherStatus getStatus() {
		// TODO statu en fonction de l'état du thread de veille
		return EventInfoNetWorkWatcherStatus.UNKNOWN_STATUS;
	}

	/**
	 * Applique les informations contenues dans un événement
	 */
	@Override
	public void applyInfo(GraphNetworkBuilder graph) {
		// TODO Auto-generated method stub

	}

	/**
	 * Renvoie les nouveaux événements
	 */
	@Override
	public Collection<EventInfo> getNewEventInfo() {
		return new Vector<EventInfo>(eventInfosNotApplied);
	}
}
