package tools.streamInFolder;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.EventInfo;
import iGoMaster.EventInfoNetWorkWatcherStatus;
import iGoMaster.EventInfoNetworkWatcher;
import iGoMaster.exception.ImpossibleStartingException;

import java.util.Collection;
import java.util.Vector;

public class EventInfoNetworkWatcherInFolder extends EventInfoNetworkWatcher {

	public EventInfoNetworkWatcherInFolder() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startWatching() throws ImpossibleStartingException {
		// TODO Auto-generated method stub
	}

	@Override
	public void stopWatching() {
		// TODO Auto-generated method stub
	}

	@Override
	public EventInfoNetWorkWatcherStatus getStatus() {
		// TODO statu en fonction de l'état du thread de veille
		return EventInfoNetWorkWatcherStatus.OFFLINE;
	}

	@Override
	public void applyInfo(GraphNetworkBuilder graph) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<EventInfo> getNewEventInfo() {
		return new Vector<EventInfo>(eventInfosNotApplied);
	}
}
