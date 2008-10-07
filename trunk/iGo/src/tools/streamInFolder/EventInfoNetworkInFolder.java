package tools.streamInFolder;

import iGoMaster.EventInfoNetWorkStatus;
import iGoMaster.EventInfoNetwork;
import iGoMaster.exception.ImpossibleStartingException;

public class EventInfoNetworkInFolder extends EventInfoNetwork {

	public EventInfoNetworkInFolder() {
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
	public EventInfoNetWorkStatus getStatus() {
		//TODO statu en fonction de l'état du thread de veille
		return EventInfoNetWorkStatus.OFFLINE;
	}
}
