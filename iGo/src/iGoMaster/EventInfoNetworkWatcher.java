package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.exception.ImpossibleStartingException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Vector;

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
	 * @uml.property name="status"
	 * @uml.associationEnd readOnly="true" inverse="eventInfoNetworkWatcher:iGoMaster.EventInfoNetWorkWatcherStatus"
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
