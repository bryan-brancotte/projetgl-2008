package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.exception.ImpossibleStartingException;

import java.util.Collection;
import java.util.Observable;

public abstract class EventInfoNetworkWatcher extends Observable {

	/**
	 * Start the watching process of the EventInfoNetwork
	 * 
	 * @throws ImpossibleStartingException
	 */
	public abstract void startWatching() throws ImpossibleStartingException;

	/**
	 * Stop the watching process of the EventInfoNetwork
	 */
	public abstract void stopWatching();

	/**
	 * give the actual status of the EventInfoNetwork
	 * 
	 * @return the actual status
	 */
	public abstract EventInfoNetWorkWatcherStatus getStatus();

	/**
	 * Apply change on the graphNetworkBuilder
	 * 
	 * @param graph
	 *            the graph where we'll apply the change
	 */
	public abstract void applyInfo(GraphNetworkBuilder graph);

	/**
	 * Allow to obtain all the new informations givent on the network. If the EventInfoNetworkWatcher is loading the new
	 * EventInfo in the collection, the getNewEventInfo will block until the loading is done
	 * 
	 * @return the collection of the new event, or a void collection if there nothing
	 */
	public abstract Collection<EventInfo> getNewEventInfo();

}
